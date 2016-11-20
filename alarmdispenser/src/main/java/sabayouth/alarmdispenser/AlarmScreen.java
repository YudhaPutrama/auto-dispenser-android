package sabayouth.alarmdispenser;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sabayouth.alarmdispenser.helper.DBAlarmHelper;
import sabayouth.alarmdispenser.helper.DBStatisticHelper;

public class AlarmScreen extends Activity {

	public final String TAG = this.getClass().getSimpleName();

	private WakeLock mWakeLock;
	private MediaPlayer mPlayer;
    private Vibrator mVibrate;

    protected String url="http://192.168.4.1/tuang";
    private DBAlarmHelper dbHelper = new DBAlarmHelper(this);
    private DBStatisticHelper dbStatisticHelper = new DBStatisticHelper(this);
    long vibratePattern[] = {0, 200, 500};

	private static final int WAKELOCK_TIMEOUT = 60 * 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setup layout
		this.setContentView(R.layout.activity_alarm_screen);

		//String name = getIntent().getStringExtra(AlarmManagerHelper.NAME);
		int timeHour = getIntent().getIntExtra(AlarmManagerHelper.TIME_HOUR, 0);
		int timeMinute = getIntent().getIntExtra(AlarmManagerHelper.TIME_MINUTE, 0);
		String tone = getIntent().getStringExtra(AlarmManagerHelper.TONE);
		
		TextView tvName = (TextView) findViewById(R.id.alarm_screen_name);
		//tvName.setText(name);
        tvName.setText("Waktunya Minum!");
		
		TextView tvTime = (TextView) findViewById(R.id.alarm_screen_time);
		tvTime.setText(String.format("%02d : %02d", timeHour, timeMinute));

        mVibrate = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		Button dismissButton = (Button) findViewById(R.id.alarm_button_dismiss);
        Button TuangButton = (Button) findViewById(R.id.alarm_button_pour);
		dismissButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				mPlayer.stop();
                mVibrate.cancel();
				finish();
			}
		});
        TuangButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.stop();
                mVibrate.cancel();
                new PourAction().execute();
                Toast.makeText(AlarmScreen.this, "Sedang mengirim perintah",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

		//Play alarm tone
		mPlayer = new MediaPlayer();
		try {
			if (tone != null && !tone.equals("")) {
				Uri toneUri = Uri.parse(tone);
				if (toneUri != null) {
					mPlayer.setDataSource(this, toneUri);
					mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
					mPlayer.setLooping(true);
					mPlayer.prepare();
					mPlayer.start();
				}
			}
            mVibrate.vibrate(vibratePattern, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Ensure wakelock release
		Runnable releaseWakelock = new Runnable() {

			@Override
			public void run() {
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

				if (mWakeLock != null && mWakeLock.isHeld()) {
					mWakeLock.release();
				}
			}
		};

		new Handler().postDelayed(releaseWakelock, WAKELOCK_TIMEOUT);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		// Set the window to keep screen on
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

		// Acquire wakelock
		PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
		if (mWakeLock == null) {
			mWakeLock = pm.newWakeLock((PowerManager.FULL_WAKE_LOCK | PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), TAG);
		}

		if (!mWakeLock.isHeld()) {
			mWakeLock.acquire();
			Log.i(TAG, "Wakelock aquired!!");
		}

	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mWakeLock != null && mWakeLock.isHeld()) {
			mWakeLock.release();
		}
	}

    private class PourAction extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            DeviceService deviceService = new DeviceService(getApplicationContext());
            String data = deviceService.makeServiceCall(url,DeviceService.GET);
            Log.d("Response: ", "> " + data);
            if(data!=null){
                try{
                    JSONObject JsonObj = new JSONObject(data);
                    JSONObject sensor = JsonObj.getJSONObject("sensor");
                    Integer code = sensor.getInt("code");
                    if(code==3){
                        Toast.makeText(getApplicationContext(),"Sedang Menuangkan",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                } finally {
                    dbStatisticHelper.addStatistic();
                }
            }
            return null;
        }
    }
}
