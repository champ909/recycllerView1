package com.example.astatic.newsapp.utilities.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by static on 7/27/2017.
 */

public class DBQuerry extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "articles_2.db";
    private static final String TAG = "dbhelper";

    public DBQuerry(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String queryString = "CREATE TABLE " + DBTable.TABLE_ARTICLES.TABLE_NAME + " (" +
                DBTable.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBTable.TABLE_ARTICLES.COL_Title + " TEXT NOT NULL, " +
                DBTable.TABLE_ARTICLES.COL_AUTHOR + " TEXT NOT NULL, " +
                DBTable.TABLE_ARTICLES.COL_DESC + " TEXT NOT NULL, " +
                DBTable.TABLE_ARTICLES.COL_URL + " TEXT NOT NULL, " +
                DBTable.TABLE_ARTICLES.COL_NEWS_IMG + " TEXT NOT NULL, " +
                DBTable.TABLE_ARTICLES.COL_PUB + " DATE " +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
