package com.example.corppool.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.corppool.model.Feed;
import com.example.corppool.model.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by deepansh on 11/12/2016.
 */
public class FeedDbHandler implements  DbHandler{

    static SQLiteHandler dbhandler;
    static SQLiteDatabase db;

    private static FeedDbHandler instance;

    private static final String TAG = FeedDbHandler.class.getSimpleName();

    //Feed table name TODO, better design, one class is cluttered
    private static final String TABLE_FEED = "feed";

    private static final String KEY_ID = "_id";

    private static final String COL_FEED_datetime = "datetime";

    private static final String COL_FEED_reqStartLoc = "reqStartLoc";
    private static final String COL_FEED_reqEndLoc = "reqEndLoc";
    private static final String COL_FEED_reqStartLat = "reqStartLat";

    private static final String COL_FEED_reqStartLong = "reqStartLong";
    private static final String COL_FEED_reqEndLat = "reqEndLat";
    private static final String COL_FEED_reqEndLong = "reqEndLong";

    private static final String COL_FEED_type = "type";
    private static final String COL_FEED_createddate = "createddate";


    public FeedDbHandler(Context context) throws  Exception{

        //get instance of db
        dbhandler = SQLiteHandler.getInstance(context, this);
    }


    public void createTable(){}

    public void updateTable(){}
    /**
     * Storing user details in database
     * */
    public void addFeed(Feed feed) {

        if(feed==null || db==null){
            return;
        }


        ContentValues values = new ContentValues();


        values.put(COL_FEED_datetime,feed.getDatetime().getTime());

        values.put(COL_FEED_reqStartLoc,feed.getStartAddress());
        values.put(COL_FEED_reqEndLoc,feed.getEndAddress());

        values.put(COL_FEED_reqStartLat,feed.getStartLoc().get_lat());
        values.put(COL_FEED_reqStartLong,feed.getStartLoc().get_long());
        values.put(COL_FEED_reqEndLat,feed.getEndLoc().get_lat());
        values.put(COL_FEED_reqEndLong, feed.getEndLoc().get_long());

        values.put(COL_FEED_type,feed.getType());

        db = dbhandler.getDatabase();
        // Inserting Row
        long id = db.insert(TABLE_FEED, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New feed inserted into sqlite: " + id);
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteFeeds() {

        db = dbhandler.getDatabase();
        // Delete All Rows
        db.delete(TABLE_FEED, null, null);
        db.close();

        Log.d(TAG, "Deleted all feeds info from sqlite");
    }

    /**
     * Getting user data from database
     * */
    public List<Feed> getFeedDetails(Feed.AvailableTypes type) {
        List<Feed> listDetails = new ArrayList<Feed>();

        String selectQuery = "SELECT  * FROM " + TABLE_FEED+" where type = '"+type.getText()+"'";

        db = dbhandler.getDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            Feed reqStartFeed = new Feed();

            reqStartFeed.setDatetime(new Date(cursor.getLong(1)));

            //populate pojo and use that to populate futher
            reqStartFeed.setStartAddress(cursor.getString(2));
            reqStartFeed.setEndAddress(cursor.getString(3));

            Location sloc = new Location();
            Location eloc = new Location();
            reqStartFeed.setStartLoc(sloc);
            reqStartFeed.setEndLoc(eloc);

            sloc.setType("Point");
            sloc.set_lat(Double.valueOf(cursor.getString(4)));
            sloc.set_long(Double.valueOf(cursor.getString(5)));

            eloc.setType("Point");
            eloc.set_lat(Double.valueOf(cursor.getString(6)));
            eloc.set_long(Double.valueOf(cursor.getString(7)));

            listDetails.add(reqStartFeed);

        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching feed from Sqlite: ");

        return listDetails;
    }

    public static Map<String,List<String>> getSchemaDefination(){

        //this is to create schema structure, only one in app lifecycle, unless upgraded
        List<String> columnStructure = new ArrayList<String>();

        //
        columnStructure.add( KEY_ID + " INTEGER PRIMARY KEY");

        columnStructure.add( COL_FEED_datetime + " integer");

        columnStructure.add( COL_FEED_reqStartLoc + " TEXT");
        columnStructure.add( COL_FEED_reqEndLoc + " TEXT ");

        columnStructure.add( COL_FEED_reqStartLat + " TEXT ");
        columnStructure.add( COL_FEED_reqStartLong + " TEXT ");
        columnStructure.add( COL_FEED_reqEndLat + " TEXT ");
        columnStructure.add(COL_FEED_reqEndLong + " TEXT ");

        columnStructure.add( COL_FEED_type + " TEXT ");
        columnStructure.add( COL_FEED_createddate + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP ");

        Map<String,List<String>> schema = new HashMap<String,List<String>>();
        schema.put(TABLE_FEED,columnStructure);

        return schema;
    }

    public String getTableName(){
        return TABLE_FEED;
    }
}
