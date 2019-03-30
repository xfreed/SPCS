package com.lembergstate.spcs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

public class HomeActivity extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ClientSocket CS;
    private int count;
    private NotifyService notifyService;
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
        currentDateTime = findViewById(R.id.Datetext);

        list = findViewById(R.id.ListVIew);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);

//        for (int i = 0; i < 30; ++i) {
//            arrayList.add("Yulian Salo ID " + currentDateTime.getText().toString());
//        }
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
//            new Notifty().notificationNew("Title", "message", this);
//        } else
//            new Notifty().notificationOld(this);

        // Start Background Service HERE


        // STOP Background Service HERE
//        Intent intent = new Intent(HomeActivity.this, NotifyService.class);
//        stopService(intent);
        CS = new ClientSocket();
        CS.setAdapter(getAdapter());
        CS.setArrayList(getArrayList());
        setInitialDateTime();
        CS.setCurrentDateTime(getCurrentDateTime());
        Intent intent = getIntent();
        CS.setPerson_ID(intent.getStringExtra("Person_ID"));
//        notifyService = new NotifyService();
//        notifyService.setAdapter(getAdapter());
//        notifyService.setArrayList(getArrayList());
//        notifyService.setCurrentDateTime(getCurrentDateTime());
//        Intent intent = new Intent(HomeActivity.this, NotifyService.class);
//        intent.putExtra("CS", (Serializable) CS);
//        startService(intent);
        count = CS.NotifData();
    }


    private void setInitialDateTime() {

        currentDateTime.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE
        ));
//        new Client().execute("");
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
            int tmp = CS.NotifData();
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

    //    private class Client extends AsyncTask<String, Void, String> {
//        BufferedReader input;
//
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                Socket socket = new Socket("192.168.43.116", 1661); //192.168.1.11
//                String message = "";
//                sendMessage(currentDateTime.getText().toString(), socket);
//                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                message = input.readLine();
//                if (message == null)
//                    message = "Yulian Salo ID " + currentDateTime.getText().toString();
//                final String finalMessage = message;
//                runOnUiThread(() -> {
//                    arrayList.clear();
//                    if (finalMessage.contains("!"))
//                        arrayList.addAll(Arrays.asList(finalMessage.split("!")));
//                    else
//                        arrayList.add(finalMessage);
//
//                    adapter.notifyDataSetChanged();
//
//                });
//                input.close();
//                socket.close();
//                this.cancel(true);
//                return "Executed";
//
//
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return "Executed";
//        }
//
//        private void sendMessage(final String message, final Socket socket) {
//            new Thread(() -> {
//                try {
//                    if (null != socket) {
//                        PrintWriter out = new PrintWriter(new BufferedWriter(
//                                new OutputStreamWriter(socket.getOutputStream())),
//                                true);
//                        out.println(message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//    }
    public Activity getAct() {
        return HomeActivity.this;
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

