package com.example.androidlabs;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDatabaseOpener extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabaseFile";
    public static final int VERSION_NUM = 1;
    public static final String TABLE_NAME = "MESSAGES";
    public static final String COL_ID = "_id";
    public static final String COL_Message ="MSG";
    public static final String COL_Type="TYPE";

    public MyDatabaseOpener(Activity ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "( "+ COL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COL_Message + " MSG,"+ COL_Type+" TYPE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i("Database upgrade", "Old version:" + oldVersion + "newVersion:"+ newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i("Database downgrade", "Old version:"+ oldVersion + "newVersion"+ newVersion );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
