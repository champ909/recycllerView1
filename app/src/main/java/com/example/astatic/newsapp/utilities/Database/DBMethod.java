package com.example.astatic.newsapp.utilities.Database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.astatic.newsapp.utilities.newsItem;

import java.util.ArrayList;


/**
 * Created by static on 7/27/2017.
 */

public class DBMethod {

        public static Cursor getAllNewsCursor(SQLiteDatabase db) {
            Cursor cursor= db.query(
                    DBTable.TABLE_ARTICLES.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DBTable.TABLE_ARTICLES.COL_PUB + " DESC");
            return cursor;
        }

    public static void getNews(SQLiteDatabase db, ArrayList<newsItem> newsItems) {
        db.beginTransaction();
        deleteAllNews(db);
        try {
            for (newsItem nItem : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(DBTable.TABLE_ARTICLES.COL_Title,
                        nItem.getTitle());
                cv.put(DBTable.TABLE_ARTICLES.COL_AUTHOR,nItem.getAuthor());
                cv.put(DBTable.TABLE_ARTICLES.COL_DESC,
                        nItem.getDescription());
                cv.put(DBTable.TABLE_ARTICLES.COL_PUB,
                        nItem.getPublishedAt());
                cv.put(DBTable.TABLE_ARTICLES.COL_NEWS_IMG,
                        nItem.getURLtoImge());
                cv.put(DBTable.TABLE_ARTICLES.COL_URL,
                        nItem.getURL());
                db.insert(DBTable.TABLE_ARTICLES.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    private static void deleteAllNews(SQLiteDatabase db) {
        db.delete(DBTable.TABLE_ARTICLES.TABLE_NAME, null, null);
    }
}

