package com.example.astatic.newsapp.utilities.Database;

import android.provider.BaseColumns;

/**
 * Created by static on 7/27/2017.
 */

public class DBTable {

    public static class TABLE_ARTICLES implements BaseColumns {

        public static final String TABLE_NAME = "articles";

        public static final String COL_AUTHOR = "author";

        public static final String COL_Title = "title";

        public static final String COL_DESC = "information";

        public static final String COL_URL = "newsurl";

        public static final String COL_NEWS_IMG = "imgurl";

        public static final String COL_PUB = "published";
    }
}