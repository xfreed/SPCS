package com.lembergstate.spcs;

import android.widget.ArrayAdapter;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

public class ClientSocket {
    private BufferedReader input;
    private String currentDateTime;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private String Person_ID;

    public void setPerson_ID(String person_ID) {
        Person_ID = person_ID;
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
            Socket socket = new Socket("192.168.43.116", 1661);
            String message; //=""
            sendMessage(currentDateTime + "/" + Person_ID, socket);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = input.readLine();
            final String finalMessage = message;
            arrayList.clear();
            if (finalMessage.contains("!"))
                arrayList.addAll(Arrays.asList(finalMessage.split("!")));
            else
                arrayList.add(finalMessage);
            input.close();
            socket.close();


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public int NotifyData() {
        return arrayList.size();
    }

    public String ToNotify() {
        return arrayList.get(NotifyData() - 1);
    }

}
