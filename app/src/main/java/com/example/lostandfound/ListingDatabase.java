package com.example.lostandfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class ListingDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Listings.db";

    private String DATABASE_PATH;

    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "currentListings";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LOCATION = "location";

    private static final String COLUMN_LOCATION_LAT = "lat";
    private static final String COLUMN_LOCATION_LONG = "long";


    public ListingDatabase(@Nullable Context context) {
//        defines the database with name and version
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        this.DATABASE_PATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        creates a string to use as the sql query and then execs the database
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_PHONE + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_LOCATION + " TEXT, " +
                        COLUMN_LOCATION_LAT + " TEXT, " +
                        COLUMN_LOCATION_LONG + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addListing(String name, String phone, String description, String date, String location, String locationlat, String locationLong){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE, phone);
        cv.put(COLUMN_DESCRIPTION, description);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_LOCATION, location);
        cv.put(COLUMN_LOCATION_LAT, locationlat);
        cv.put(COLUMN_LOCATION_LONG, locationLong);

        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Listing Added", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteListing(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        finds the given database from the id and calls the delete function
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Did not delete", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void setDATABASE_PATH(String path) {
        this.DATABASE_PATH = path;
    }

    String getDbPath() {
        return DATABASE_PATH;
    }
}
