package sabayouth.autodispenser;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Created by Yudha Putrama on 9/23/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        /*
        long alarmMsgId = intent.getLongExtra(AlarmMsg.COL_ID, -1);
        long alarmId = intent.getLongExtra(AlarmMsg.COL_ALARMID, -1);

        AlarmMsg alarmMsg = new AlarmMsg(alarmMsgId);
        alarmMsg.setStatus(AlarmMsg.EXPIRED);
        alarmMsg.persist(RemindMe.db);

        Alarm alarm = new Alarm(alarmId);
        alarm.load(RemindMe.db);

        Notification n = new Notification(R.drawable.ic_launcher, alarm.getName(), System.currentTimeMillis());
        PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(), 0);

        n.setLatestEventInfo(context, "Remind Me", alarm.getName(), pi);
        if (RemindMe.isVibrate()) {
            n.defaults |= Notification.DEFAULT_VIBRATE;
        }
        if (alarm.getSound()) {
            n.sound = Uri.parse(RemindMe.getRingtone());
//			n.defaults |= Notification.DEFAULT_SOUND;
        }
        n.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify((int)alarmMsgId, n);
        */
        /*
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_settings)
                .setContentTitle("Auto Dipenser")
                .setContentText("Time for drink");

        Intent resultIntent = new Intent();

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationMgr.notify(123, notificationBuilder);

        */

        Intent i = new Intent(); //Edit with Notification Receiver

        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, 0);
        Notification n = new Notification.Builder(context)
                .setContentTitle("Water Alarm - Auto Dispenser")
                .setContentText("Waktunya untuk minum")
                .setSmallIcon(R.drawable.ic_action_settings)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .addAction(R.drawable.ic_action_settings, "Tuangkan", pendingIntent)
                .addAction(R.drawable.ic_action_settings, "Sudah Minum", pendingIntent)
                .addAction(R.drawable.ic_action_settings, "Nanti", pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }

}