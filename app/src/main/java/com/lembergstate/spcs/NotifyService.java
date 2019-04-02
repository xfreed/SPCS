//package com.lembergstate.spcs;
//
//import android.app.Activity;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.app.TaskStackBuilder;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.widget.ArrayAdapter;
//
//import java.io.Serializable;
//import java.io.SerializablePermission;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import androidx.annotation.RequiresApi;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//public class NotifyService extends Service implements Serializable {
//    private Timer timer;
//    private String currentDateTime;
//    private ArrayList<String> arrayList;
//    private ArrayAdapter<String> adapter;
//    private int count = 0;
//    private ClientSocket CS;
//
//    @Override
//    public void onStart(Intent intent, int startId) {
//        super.onStart(intent, startId);
//        Bundle extras = intent.getExtras();
//        CS = (ClientSocket) extras.getSerializable("CS");
////        currentDateTime = (String) extras.get("cdt");
////        arrayList = (ArrayList<String>) extras.get("arr");
////        adapter = (ArrayAdapter<String>) extras.get("adp");
////        CS = new ClientSocket();
////        CS.setAdapter(getAdapter());
////        CS.setArrayList(getArrayList());
////        CS.setCurrentDateTime(getCurrentDateTime());
//        start();
//
//    }
//
//    public String getCurrentDateTime() {
//        return currentDateTime;
//    }
//
//    public void setCurrentDateTime(String currentDateTime) {
//        this.currentDateTime = currentDateTime;
//    }
//
//    public ArrayList<String> getArrayList() {
//        return arrayList;
//    }
//
//    public void setArrayList(ArrayList<String> arrayList) {
//        this.arrayList = arrayList;
//    }
//
//    public ArrayAdapter<String> getAdapter() {
//        return adapter;
//    }
//
//    public void setAdapter(ArrayAdapter<String> adapter) {
//        this.adapter = adapter;
//    }
//
//    public NotifyService() {
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
//    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        stop();
//    }
//
//    private TimerTask timerTask = new TimerTask() {
//
//        @Override
//        public void run() {
//            int tmp = new ClientSocket().NotifyData();
//            if (tmp > count) {
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
//                    new Notify().notificationNew("New data in app",
//                            new ClientSocket().ToNotify(), new HomeActivity().getAct());
//                } else
//                    new Notify().notificationOld("New data in app",
//                            new ClientSocket().ToNotify(), new HomeActivity().getAct());
//            }
//        }
//    };
//
//    private void start() {
//        if (timer != null) {
//            return;
//        }
//        timer = new Timer();
//        timer.scheduleAtFixedRate(timerTask, 0, 30000);
//    }
//
//    private void stop() {
//        timer.cancel();
//        timer = null;
//    }
//
//    private class Notify {
//        public void notificationOld(String title, String message, Context con) {
//            int notificationId = createID();
//            NotificationCompat.Builder builder =
//                    new NotificationCompat.Builder(con)
//                            .setContentTitle(title)
//                            .setContentText(message)
//                            .setSmallIcon(R.drawable.logo);
//            Notification notification = builder.build();
//
//            NotificationManager notificationManager =
//                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            notificationManager.notify(notificationId, notification);
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
//        public void notificationNew(String title, String message, Context context) {
//            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            int notificationId = createID();
//            String channelId = "channel-id";
//            String channelName = "Channel Name";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                NotificationChannel mChannel = new NotificationChannel(
//                        channelId, channelName, importance);
//                notificationManager.createNotificationChannel(mChannel);
//            }
//
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
//                    .setSmallIcon(R.drawable.logo)//R.mipmap.ic_launcher
//                    .setContentTitle(title)
//                    .setContentText(message)
//                    .setVibrate(new long[]{100, 250})
//                    .setLights(Color.YELLOW, 500, 5000)
//                    .setAutoCancel(true)
//                    .setColor(ContextCompat.getColor(context, R.color.aluminum));
//
//            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//            stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
//            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//            mBuilder.setContentIntent(resultPendingIntent);
//
//            notificationManager.notify(notificationId, mBuilder.build());
//        }
//
//        public int createID() {
//            Date now = new Date();
//            int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss", Locale.FRENCH).format(now));
//            return id;
//        }
//    }
//
//}
