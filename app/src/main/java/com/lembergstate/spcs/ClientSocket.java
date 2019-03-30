package com.lembergstate.spcs;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

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

public class ClientSocket extends AsyncTask<String, Void, String> {
    private BufferedReader input;
    private String currentDateTime;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private Activity activity;

    ClientSocket(String currentDateTime, ArrayList<String> arrayList, ArrayAdapter<String> adapter,
                 Activity activity) {
        this.activity = activity;
        this.adapter = adapter;
        this.arrayList = arrayList;
        this.currentDateTime = currentDateTime;
        this.execute("");
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            Socket socket = new Socket("192.168.43.116", 1661); //192.168.1.11
            String message = "";
            sendMessage(currentDateTime, socket);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = input.readLine();
//            if (message == null)
//                message = "Yulian Salo ID " + currentDateTime.toString();
            final String finalMessage = message;
            activity.runOnUiThread(() -> {
                arrayList.clear();
                if (finalMessage.contains("!"))
                    arrayList.addAll(Arrays.asList(finalMessage.split("!")));
                else
                    arrayList.add(finalMessage);

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
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }
}
