package com.lembergstate.spcs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.atomic.AtomicReference;

public class NavigationActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private ListView ChildList;

    String[] ChildName = {
            "Aragorn", "Crampus",
            "Protps", "Artos",
            "Shong", ""
    };

    String[] date = {
            "Today", "Today",
            "yesterday", "Today",
            "yesterday", ""
    };
    String[] time = {
            "8:20", "8:25",
            "8:27", "8:31",
            "8:26", ""
    };
    String[] InOrOut = {
            "In", "In",
            "In", "Out",
            "In", ""
    };
    boolean[] phase = {false, false, false, false, false, true};
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_info:
                    mTextMessage.setText(R.string.title_info);
                    return true;
                case R.id.navigation_children:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ChildrenListAdapter adapter = new ChildrenListAdapter(this, ChildName, date, time, InOrOut, phase);
        ChildList = findViewById(R.id.ChilderList);
        ChildList.setAdapter(adapter);
        ChildList.setOnItemClickListener((parent, view, position, id) -> {
            // TODO Auto-generated method stub
            if(position == 0) {
                //code specific to first list item
                Toast.makeText(getApplicationContext(),"Place Your First Option Code",Toast.LENGTH_SHORT).show();
            }

            else if(position == 1) {
                //code specific to 2nd list item
                Toast.makeText(getApplicationContext(),"Place Your Second Option Code",Toast.LENGTH_SHORT).show();
            }

            else if(position == 2) {

                Toast.makeText(getApplicationContext(),"Place Your Third Option Code",Toast.LENGTH_SHORT).show();
            }
            else if(position == 3) {

                Toast.makeText(getApplicationContext(),"Place Your Forth Option Code",Toast.LENGTH_SHORT).show();
            }
            else if(position == 4) {

                Toast.makeText(getApplicationContext(),"Place Your Fifth Option Code",Toast.LENGTH_SHORT).show();
            } else if (position == 5) {


                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.dialog_add_child, null);

                final AlertDialog alertD = new AlertDialog.Builder(this).create();

                EditText userInput = promptView.findViewById(R.id.child_code);

                Button cancel = promptView.findViewById(R.id.cancel_button);

                Button ok = promptView.findViewById(R.id.ok_button);

                cancel.setOnClickListener(v -> {
                    Log.d("TEST", "CANCEL BTN");
                    alertD.dismiss();
                });

                ok.setOnClickListener(v -> {
                    Log.d("TEST", "OK BTN");
                    String Code;
                    Code = ((EditText) alertD.findViewById(R.id.child_code)).getText().toString();
                    Log.d("TEST", "CODE: "+Code);

                });
                alertD.setView(promptView);

                alertD.show();


            }
        });
    }

}
