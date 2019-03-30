package com.lembergstate.spcs;

import android.app.Activity;
import android.os.Looper;
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
import java.util.logging.Handler;

public class ClientSocket {
    private BufferedReader input;
    private String currentDateTime;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private String Person_ID;

    public String getPerson_ID() {
        return Person_ID;
    }

    public void setPerson_ID(String person_ID) {
        Person_ID = person_ID;
    }

    ClientSocket() {

    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public void setAdapter(ArrayAdapter<String> adapter) {
        this.adapter = adapter;
    }

    public void GetData() {
        try {
            Socket socket = new Socket("192.168.43.116", 1661); //192.168.1.11
            String message = "";
            sendMessage(currentDateTime + "/" + Person_ID, socket);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = input.readLine();
//            if (message == null)
//                message = "Yulian Salo ID " + currentDateTime.toString();
            final String finalMessage = message;
//            arrayList.clear();
//            if (finalMessage.contains("!"))
//                arrayList.addAll(Arrays.asList(finalMessage.split("!")));
//            else
//                arrayList.add(finalMessage);

//            adapter.notifyDataSetChanged();
            arrayList.clear();
            if (finalMessage.contains("!"))
                arrayList.addAll(Arrays.asList(finalMessage.split("!")));
            else
                arrayList.add(finalMessage);
//            activity.runOnUiThread(
//                    new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                        }
//                    });

            input.close();
            socket.close();


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public ArrayAdapter<String> getAdapter() {
        return adapter;
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

    public int NotifData() {
        return arrayList.size();
    }

    public String ToNotify() {
        return arrayList.get(NotifData() - 1);
    }

}
