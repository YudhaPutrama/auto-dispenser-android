package sabayouth.alarmdispenser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sabayouth.alarmdispenser.adapter.AlarmListAdapter;
import sabayouth.alarmdispenser.connection.DeviceWifiService;
import sabayouth.alarmdispenser.helper.DBAlarmHelper;
import sabayouth.alarmdispenser.helper.DBStatisticHelper;
import sabayouth.alarmdispenser.model.AlarmModel;

public class MainActivity extends ActionBarActivity{

	private AlarmListAdapter mAdapter;
	private Context mContext;
    private ListView mListView;
    private Menu menuMain;
    private static String url = "http://192.168.4.1/";
    private JSONObject status=null;
    private DBAlarmHelper dbHelper = new DBAlarmHelper(this);
    private DBStatisticHelper dbStatisticHelper = new DBStatisticHelper(this);
    private Integer waterTotal = 2000;
    private Integer waterUsage;
    private Integer waterRemain;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mContext = this;

		setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());
        //new GetStatus().execute();
		mListView = (ListView) findViewById(R.id.listView);
		mListView.setAdapter(mAdapter);
	}

    private void getProgress(){
        waterUsage = dbStatisticHelper.countToday()*250;
        waterRemain=waterTotal-waterUsage;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView textRemain = (TextView) findViewById(R.id.waterRemain);
        TextView textUsage = (TextView) findViewById(R.id.waterUsage);
        textRemain.setText(waterRemain.toString()+" ml");
        textUsage.setText(waterUsage.toString()+" ml");
        progressBar.setMax(waterTotal);
        progressBar.setProgress(waterUsage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProgress();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
        menuMain=menu;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_new_alarm: {
				startAlarmDetailsActivity(-1);
				break;
			}
            case R.id.action_wifi_connect:{
                //url="http://192.168.4.1/tuang/";
                //new GetStatus().execute();
                //new DeviceWifiService(getApplicationContext()).connectAutoDispenser();
                startActivity(new Intent(this,DeviceActivity.class));
                break;
            }
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
	        mAdapter.setAlarms(dbHelper.getAlarms());
	        mAdapter.notifyDataSetChanged();
	    }
	}

    public void setAlarmEnabled(long id, boolean isEnabled) {
		AlarmManagerHelper.cancelAlarms(this);
		
		AlarmModel model = dbHelper.getAlarm(id);
		model.isEnabled = isEnabled;
		dbHelper.updateAlarm(model);
		
		AlarmManagerHelper.setAlarms(this);
	}

	public void startAlarmDetailsActivity(long id) {
		Intent intent = new Intent(this, AlarmDetailsActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
	}
	public void tampilPesan(String text, int duration){
        Toast.makeText(this,text,duration).show();
    }
	public void deleteAlarm(long id) {
		final long alarmId = id;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Please confirm")
		.setTitle("Delete set?")
		.setCancelable(true)
		.setNegativeButton("Cancel", null)
		.setPositiveButton("Ok", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//Cancel Alarms
				AlarmManagerHelper.cancelAlarms(mContext);
				//Delete alarm from DB by id
				dbHelper.deleteAlarm(alarmId);
				//Refresh the list of the alarms in the adaptor
				mAdapter.setAlarms(dbHelper.getAlarms());
				//Notify the adapter the data has changed
				mAdapter.notifyDataSetChanged();
				//Set the alarms
				AlarmManagerHelper.setAlarms(mContext);
			}
		}).show();
	}

    private class GetStatus extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            DeviceWifiService deviceWifiService = new DeviceWifiService(getApplicationContext());

            // Making a request to url and getting response
            String jsonStr = deviceWifiService.makeServiceCall(url, DeviceWifiService.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    //contacts = jsonObj.getJSONArray(TAG_CONTACTS);
                    status = jsonObj.getJSONObject("status");

                    String irValue = status.getString("irValue");
                    String usValue = status.getString("usValue");
                    Boolean PumpOn = status.getString("pumpIs").equals("ON");
                    Log.d("JSON",jsonStr);
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                //Toast.makeText(MainActivity.this, "Tidak tersambung ke Auto Dispenser", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            //if (pDialog.isShowing())
//                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            /*
             ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, contactList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
                    TAG_PHONE_MOBILE }, new int[] { R.id.name,
                    R.id.email, R.id.mobile });

            setListAdapter(adapter);
            */
        }

    }
    /*
    public class NetworkChange extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            WifiManager wifiMgr = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            if (wifiMgr.getConnectionInfo().getSSID().equals("\"AutoDispenser\"")){
                Toast.makeText(context, "Connected to AutoDispenser", Toast.LENGTH_SHORT).show();
            }
        }
    }
    */
}
