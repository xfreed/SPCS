package com.lembergstate.spcs;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ChildrenListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] childName;
    private final String[] date;
    private final String[] time;
    private final String[] inOrOut;
    private final boolean[] phase;



    public ChildrenListAdapter(Activity context, String[] childName, String[] date, String[] time,
                               String[] inOrOut, boolean[] phase) {
        super(context,R.layout.childrenlist,childName);
        this.context = context;
        this.childName = childName;
        this.date = date;
        this.time = time;
        this.inOrOut = inOrOut;
        this.phase = phase;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView;
        if (phase[position]) {
            rowView = inflater.inflate(R.layout.add, null, true);
            ImageView plus = rowView.findViewById(R.id.plus);
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                plus.setImageDrawable(context.getResources().getDrawable(R. drawable.ic_plus_one_white_24dp, context.getTheme()));
            } else {
                plus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_plus_one_white_24dp));
            }
            return rowView;
        }
        Log.d("TEST", String.valueOf(position));
        rowView = inflater.inflate(R.layout.childrenlist, null, true);
        TextView child_name = rowView.findViewById(R.id.ChildName);
        ImageView three_dots = rowView.findViewById(R.id.threeDots);
        TextView Text_date = rowView.findViewById(R.id.LastDate);
        TextView Text_time = rowView.findViewById(R.id.LastTime);
        TextView TinOrOut = rowView.findViewById(R.id.InOrOut);
        child_name.setText(childName[position]);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            three_dots.setImageDrawable(context.getResources().getDrawable(R. drawable.ic_more_horiz_white_24dp, context.getTheme()));
        } else {
            three_dots.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_more_horiz_white_24dp));
        }
        Text_date.setText(date[position]);
        Text_time.setText(time[position]);
        TinOrOut.setText(inOrOut[position]);
        three_dots.setOnClickListener(v -> {
            Toast.makeText(context,"Click on DOTS",Toast.LENGTH_SHORT).show();

        });
        return rowView;

    }
}
