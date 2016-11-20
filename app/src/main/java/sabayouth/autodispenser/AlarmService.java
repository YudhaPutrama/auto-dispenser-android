package sabayouth.autodispenser;

import android.app.IntentService;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

import java.util.List;

import sabayouth.autodispenser.model.Alarm;
import sabayouth.autodispenser.model.DbHelper;

/**
 * Created by Yudha Putrama on 9/23/2015.
 * App Name     : InfoFBL
 */
public class AlarmService extends IntentService {

    private static final String TAG = "AlarmService";

    public static final String POPULATE = "POPULATE";
    public static final String CREATE = "CREATE";
    public static final String CANCEL = "CANCEL";

    private IntentFilter matcher;

    public AlarmService(){
        super(TAG);
        matcher.addAction(POPULATE);
        matcher.addAction(CREATE);
        matcher.addAction(CANCEL);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        List<Alarm> listAlarm = AutoDispenser.dbHelper.listAlarms();


        //String
    }
}
