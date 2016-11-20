package sabayouth.autodispenser;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import sabayouth.autodispenser.model.Alarm;

/**
 * Created by Yudha Putrama on 9/23/2015.
  */
public class AlarmSetter extends BroadcastReceiver {
    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    private Integer hour;
    private Integer minute;


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, AlarmService.class);
        service.setAction(AlarmService.CREATE);
        //context.startService(service);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmReceiver.class);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*60*60*24, alarmIntent);

    }
}
