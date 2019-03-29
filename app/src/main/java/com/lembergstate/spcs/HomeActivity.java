package com.lembergstate.spcs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
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
        setInitialDateTime();

        list = findViewById(R.id.ListVIew);
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        list.setAdapter(adapter);
        for (int i=0;i<30;++i){
            arrayList.add("Yulian Salo ID "+ currentDateTime.getText().toString());
        }
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
            new Client().execute("");

        }
    };

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 5000);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    private class Client extends AsyncTask<String, Void, String> {
        BufferedReader input;

        @Override
        protected String doInBackground(String... params) {
            try {
                Socket socket = new Socket("192.168.1.11", 1661);
                final String message;
                sendMessage(currentDateTime.getText().toString(),socket);
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                message = input.readLine();
//            if(message==null)
//                message =  "Yulian Salo ID "+ currentDateTime.getText().toString();
//            final String finalMessage = message;
                runOnUiThread(() -> {
//                arrayList.clear();
                    if (message.contains("!"))
                        arrayList.addAll(Arrays.asList( message.split("!")));
                    else
                        arrayList.add(message);

                    adapter.notifyDataSetChanged();

                });
                input.close();
                socket.close();
                this.cancel(true);
                return "Executed";


            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Executed";
        }

        private void sendMessage(final String message, final Socket socket) {
            new Thread(() -> {
                try {
                    if (null != socket) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}

