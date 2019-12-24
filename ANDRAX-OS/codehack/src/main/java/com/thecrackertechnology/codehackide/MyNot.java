package com.thecrackertechnology.codehackide;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class MyNot extends Service {

    @Override
    public void onCreate() {

        Intent notificationIntent = new Intent(getApplicationContext(), com.thecrackertechnology.codehackide.MainActivityCodeHackIDE.class);


        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        int notificationId = 2;
        String channelId = "channel-02";
        String channelName = "CodeHACKIDE";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle("CodeHACK-IDE Running")
                .setContentText("Running in background")
                .setSubText("Close your project to stop")
                .setSmallIcon(R.drawable.codehack)
                .setChannelId(channelId)
                .setPriority(importance)
                .setAutoCancel(false)
                .setOngoing(true)
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(2, notification);

        startForeground(2, notification);


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
