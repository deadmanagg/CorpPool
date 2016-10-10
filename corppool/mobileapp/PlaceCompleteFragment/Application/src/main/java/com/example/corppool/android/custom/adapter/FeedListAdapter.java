package com.example.corppool.android.custom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corppool.model.Feed;
import com.example.google.playservices.placecompletefragment.R;

import java.util.List;

/**
 * Created by deepansh on 10/9/2016.
 */
public class FeedListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final Feed[]  values;

    public FeedListAdapter(Context context, Feed[] values) {
        super(context, R.layout.fragment_feed);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_feed, parent, false);
        //TextView startLoc = (TextView) rowView.findViewById(R.id.startLoc);
        //TextView endLoc = (TextView) rowView.findViewById(R.id.endLoc);

        //startLoc.setText(Double.valueOf(values[position].getStartLoc().get_lat()).toString());
        //endLoc.setText(Double.valueOf(values[position].getEndLoc().get_lat()).toString());

        // Change icon based on name
        Feed s = values[position];

        TextView milesAway = (TextView) rowView.findViewById(R.id.milesAway);;
        TextView startAddress = (TextView) rowView.findViewById(R.id.startAddress);;
        TextView endAddress = (TextView) rowView.findViewById(R.id.endAddress);;
        TextView minutesAway= (TextView) rowView.findViewById(R.id.minutesAway);

        //Now set values
        milesAway.setText(s.getnMilesAway()+" Miles Away");
        startAddress.setText(s.getStartAddress());
        endAddress.setText(s.getEndAddress());
        minutesAway.setText("Starting in "+s.getxMinutesAway()+" min ");

        System.out.println(s);

        return rowView;
    }

    @Override
    public int getCount() {
        return values.length;
    }
}
