package toussaint.projet_android.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import toussaint.projet_android.MainApp;
import toussaint.projet_android.R;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";

    public void onReceive(Context context, Intent intent) {

        if(Utils.getNotification()){
            Intent myIntent = new Intent(context, MainApp.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);

            Notification myNotification = new NotificationCompat.Builder(context)
                    .setContentTitle("Nouveauté parrainage !")
                    .setContentText("Venez-vite voir notre nouveau programme")
                    .setTicker("Nouveauté !")
                    .setWhen(System.currentTimeMillis())
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setSmallIcon(R.drawable.logo)
                    .build();

            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            int id = intent.getIntExtra(NOTIFICATION_ID, 0);
            notificationManager.notify(id, myNotification);
        }

    }
}