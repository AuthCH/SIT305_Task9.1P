package com.example.lostfoundmapapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {

    //Declare variables
    private Context context;
    private static final String DATABASE_NAME = "Lostfounditem5.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Lostfounditem";
    private static final String COLUMN_ID = "id";
    private static final String COLOMN_STATUS = "status";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ITEM = "item";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LONG = "long";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOMN_STATUS + " TEXT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_ITEM + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_LAT + " TEXT, " +
                COLUMN_LONG + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void additem(String status,String name, String item, String location,String lat,String lng){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLOMN_STATUS, status);
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_ITEM, item);
        contentValues.put(COLUMN_LOCATION, location);
        contentValues.put(COLUMN_LAT, lat);
        contentValues.put(COLUMN_LONG, lng);


        long result = database.insert(TABLE_NAME,null,contentValues);

        if (result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
        }

    }

    //Get Data from database
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;
    }
    //Delete function
    void delete(String row_id){
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
