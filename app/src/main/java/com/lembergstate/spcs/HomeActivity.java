package com.lembergstate.spcs;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.*;

public class HomeActivity extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ClientSocket CS;
    private int count;
    //    private NotifyService notifyService;
    Calendar dateAndTime = Calendar.getInstance();
    TextView currentDateTime;
    Button Datebtn;
    DatePickerDialog.OnDateSetListener d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        d = (view, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

        };
        Datebtn = findViewById(R.id.SetDate);
        Datebtn.setOnClickListener(view -> new DatePickerDialog(HomeActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show());
        currentDateTime = findViewById(R.id.DateText);

        list = findViewById(R.id.ListVIew);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

//        for (int i = 0; i < 30; ++i) {
//            arrayList.add("Yulian Salo ID " + currentDateTime.getText().toString());
//        }

        CS = new ClientSocket();
        CS.setAdapter(getAdapter());
        CS.setArrayList(getArrayList());
        setInitialDateTime();
        CS.setCurrentDateTime(getCurrentDateTime());
        Intent intent = getIntent();
        CS.setPerson_ID(intent.getStringExtra("Person_ID"));
        count = CS.NotifyData();
    }


    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE
        ));
        start();
    }

    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            CS.GetData();
            arrayList = CS.getArrayList();
            adapter = CS.getAdapter();
            runOnUiThread(() -> adapter.notifyDataSetChanged());
            int tmp = CS.NotifyData();
            if (tmp > count) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                    new Notify().notificationNew("New data in app",
                            CS.ToNotify(), HomeActivity.this);
                } else
                    new Notify().notificationOld("New data in app",
                            CS.ToNotify(), HomeActivity.this);
                count = tmp;
            }
        }
    };

    public void start() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }


    public ArrayAdapter<String> getAdapter() {
        return adapter;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public String getCurrentDateTime() {
        return currentDateTime.getText().toString();
    }

    private class Notify {
        public void notificationOld(String title, String message, Context con) {
            int notificationId = createID();
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(con)
                            .setContentTitle(title)
                            .setContentText(message)
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

