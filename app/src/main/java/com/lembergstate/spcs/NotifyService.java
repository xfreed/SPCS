package com.lembergstate.spcs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class NotifyService extends Service {
    private Timer timer;

    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stop();
    }

    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {

        }
    };

    private void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 30000);
    }

    private void stop() {
        timer.cancel();
        timer = null;
    }

    private class Notifty {
        public void notificationOld(Context con) {
            int notificationId = createID();
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(con)
                            .setContentTitle("Title")
                            .setContentText("Notification text")
                            .setSmallIcon(R.drawable.logo);
            Notification notification = builder.build();

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void notificationNew(String title, String message, Context context) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            int notificationId = createID();
            String channelId = "channel-id";
            String channelName = "Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.logo)//R.mipmap.ic_launcher
                    .setContentTitle(title)
                    .setContentText(message)
                    .setVibrate(new long[]{100, 250})
                    .setLights(Color.YELLOW, 500, 5000)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(context, R.color.aluminum));

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            notificationManager.notify(notificationId, mBuilder.build());
        }

        public int createID() {
            Date now = new Date();
            int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.FRENCH).format(now));
            return id;
        }
    }

}
