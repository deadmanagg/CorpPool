package com.example.corppool.db;

/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "android_cp_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";


    //Feed table name TODO, better design, one class is cluttered
    private static final String TABLE_FEED = "feed";

    private static final String COL_FEED_date_display = "date_display";
    private static final String COL_FEED_date = "date";
    private static final String COL_FEED_time = "time";

    private static final String COL_FEED_reqStartLoc = "reqStartLoc";
    private static final String COL_FEED_reqEndLoc = "reqEndLoc";
    private static final String COL_FEED_reqStartLat = "reqStartLat";

    private static final String COL_FEED_reqStartLong = "reqStartLong";
    private static final String COL_FEED_reqEndLat = "reqEndLat";
    private static final String COL_FEED_reqEndLong = "reqEndLong";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        //create feed table now
        CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_FEED + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + COL_FEED_date_display + " TEXT,"
                + COL_FEED_date + " TEXT UNIQUE," + COL_FEED_time + " TEXT,"
                + COL_FEED_reqStartLoc + " TEXT ," + COL_FEED_reqEndLoc + " TEXT,"
                + COL_FEED_reqStartLat + " TEXT ," + COL_FEED_reqStartLong + " TEXT,"
                + COL_FEED_reqEndLat + " TEXT ,"+ COL_FEED_reqEndLong + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);


        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }



        /**
         * Storing user details in database
         * */
        public void addFeed(String date_display, String date, String time, String reqStartLoc,String reqEndLoc, String reqStartLat, String reqStartLong, String reqEndLat,String reqEndLong) {
            SQLiteDatabase db = getWritableDatabase();

            ContentValues values = new ContentValues();


           values.put(COL_FEED_date_display,date_display);
            values.put(COL_FEED_date,date);
            values.put(COL_FEED_time,time);

            values.put(COL_FEED_reqStartLoc,reqStartLoc);
            values.put(COL_FEED_reqEndLoc,reqEndLoc);
            values.put(COL_FEED_reqStartLat,reqStartLat);

            values.put(COL_FEED_reqStartLong,reqStartLong);
            values.put(COL_FEED_reqEndLat,reqEndLat);
            values.put(COL_FEED_reqEndLong, reqEndLong);


            // Inserting Row
            long id = db.insert(TABLE_FEED, null, values);
            db.close(); // Closing database connection

            Log.d(TAG, "New feed inserted into sqlite: " + id);
        }

        /**
         * Getting user data from database
         * */
        public HashMap<String, String> getFeedDetails() {
            HashMap<String, String> feed = new HashMap<String, String>();
            String selectQuery = "SELECT  * FROM " + TABLE_FEED;

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            // Move to first row
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                feed.put(COL_FEED_date_display,cursor.getString(1));
                feed.put(COL_FEED_date,cursor.getString(2));
                feed.put(COL_FEED_time,cursor.getString(3));

                feed.put(COL_FEED_reqStartLoc,cursor.getString(4));
                feed.put(COL_FEED_reqEndLoc,cursor.getString(5));
                feed.put(COL_FEED_reqStartLat,cursor.getString(6));

                feed.put(COL_FEED_reqStartLong,cursor.getString(7));
                feed.put(COL_FEED_reqEndLat,cursor.getString(8));
                feed.put(COL_FEED_reqEndLong,cursor.getString(9));
            }
            cursor.close();
            db.close();
            // return user
            Log.d(TAG, "Fetching feed from Sqlite: " + feed.toString());

            return feed;
        }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteFeeds() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FEED, null, null);
        db.close();

        Log.d(TAG, "Deleted all feeds info from sqlite");
    }

}