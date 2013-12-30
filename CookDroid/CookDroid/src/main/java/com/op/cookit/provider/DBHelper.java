package com.op.cookit.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.op.cookit.AppBase;

public class DBHelper {

    SQLiteDatabase db;
    Context context;
    DatabaseHelper dbHelper;
    static final String PRODUCTS = "products";


    public static final String ID = "id";
    public static final String NAME = "name";

    static final String DB_NAME = "DB_COOKDROID";


    public DBHelper(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, AppBase.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    PRODUCTS + " (" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NAME + " TEXT NOT NULL"
                    + ")");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("DbTool.onUpgrade", "old:" + oldVersion + " new:"
                    + newVersion);
            if (oldVersion == 5) {
            }
            onCreate(db);
        }
    }


    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

}
