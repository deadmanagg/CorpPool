package com.example.corppool.android.custom.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.corppool.model.Feed;
import com.example.corppool.model.TimeDifference;
import com.example.corppool.util.CommonUtils;
import com.example.corppool.util.StringUtils;
import com.example.google.playservices.placecompletefragment.R;

import java.util.List;

/**
 * Created by deepansh on 10/9/2016.
 */
public class FeedListAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final List<Feed>  values;

    private Feed reqStartFeed;

    public FeedListAdapter(Context context, List<Feed> values,Feed reqStartFeed) {
        super(context, R.layout.fragment_feed);
        this.context = context;
        this.values = values;
        this.reqStartFeed = reqStartFeed;
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
        Feed s = values.get(position);

        TextView milesAway = (TextView) rowView.findViewById(R.id.milesAway);;
        TextView startAddress = (TextView) rowView.findViewById(R.id.startAddress);;
        TextView endAddress = (TextView) rowView.findViewById(R.id.endAddress);;
        TextView minutesAway= (TextView) rowView.findViewById(R.id.minutesAway);

        //Now set values
        //calculate miles and then minutes, based on 30 mph
        double distance = CommonUtils.getDistanceInKm(s.getStartLoc().get_lat(), s.getStartLoc().get_long(), reqStartFeed.getStartLoc().get_lat(), reqStartFeed.getStartLoc().get_long());

        milesAway.setText(distance+" km");
        startAddress.setText(StringUtils.substring(s.getStartAddress(),0, 30));
        endAddress.setText(StringUtils.substring(s.getEndAddress(), 0, 30));

        //calculate time
        String date1 = CommonUtils.convertToInternationalDateFormat(CommonUtils.convertTextDateToDate(reqStartFeed.getDate()))+" "+reqStartFeed.getTime();
        String date2 = CommonUtils.convertToInternationalDateFormat(s.getDate())+" "+s.getTime();

        TimeDifference timeDiff = null;
        try {
             timeDiff = CommonUtils.getTimeDifference(date1, date2);
        }catch(Exception e){
            System.out.println(e);
            timeDiff = new TimeDifference();
        }
        minutesAway.setText(timeDiff.format());
        System.out.println(s);

        return rowView;
    }

    @Override
    public int getCount() {
        return values.size();
    }
}
