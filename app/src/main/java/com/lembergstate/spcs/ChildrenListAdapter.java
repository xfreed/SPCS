package com.lembergstate.spcs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChildrenListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] childName;
    private final String[] date;
    private final String[] time;
    private final String[] inOrOut;


    public ChildrenListAdapter(Activity context, String[] childName, String[] date, String[] time, String[] inOrOut) {
        super(context,R.layout.childrenlist,childName);
        this.context = context;
        this.childName = childName;
        this.date = date;
        this.time = time;
        this.inOrOut = inOrOut;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.childrenlist, null,true);

        TextView childname = rowView.findViewById(R.id.ChildName);
        ImageView threedots = rowView.findViewById(R.id.threeDots);
        TextView Tdate = rowView.findViewById(R.id.LastDate);
        TextView Ttime = rowView.findViewById(R.id.LastTime);
        TextView TinOrOut = rowView.findViewById(R.id.InOrOut);
        childname.setText(childName[position]);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            threedots.setImageDrawable(context.getResources().getDrawable(R. drawable.ic_more_horiz_white_24dp, context.getTheme()));
        } else {
            threedots.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_more_horiz_white_24dp));
        }
        Tdate.setText(date[position]);
        Ttime.setText(time[position]);
        TinOrOut.setText(inOrOut[position]);

        return rowView;

    }
}
