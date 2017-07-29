package com.example.astatic.newsapp.utilities;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.astatic.newsapp.R;
import com.example.astatic.newsapp.utilities.Database.DBMethod;
import com.example.astatic.newsapp.utilities.Database.DBQuerry;
import com.example.astatic.newsapp.utilities.Database.DBTable;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void>, ForecastAdapter.ItemClickListener {


    private TextView errorTextView;
    ProgressBar ProgressBAR;
    private RecyclerView rv;

    private ForecastAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private static final int GITHUB_SEARCH_LOADER =1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadonfirstrun();
        rv = (RecyclerView) findViewById(R.id.rv1);
        errorTextView = (TextView) findViewById(R.id.error_message);
        ProgressBAR = (ProgressBar) findViewById(R.id.progressbar);
        rv.setLayoutManager(new LinearLayoutManager(this));

        db= new DBQuerry(MainActivity.this).getReadableDatabase();
        cursor = DBMethod.getAllNewsCursor(db);
        adapter = new ForecastAdapter(cursor, this);
        rv.setAdapter(adapter);

    }

    private void loadonfirstrun()
    {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean data  = defaultSharedPreferences.getBoolean("data", true);
        if (data) {

            LoaderManager loaderManager=getSupportLoaderManager();
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER,null,this).forceLoad();
            SharedPreferences.Editor editor = defaultSharedPreferences.edit();
            editor.putBoolean("data", false);
            editor.commit();
        }
    }

//    public class NewsApiTask extends AsyncTask<String, Void, ArrayList<newsItem>> {
//        ArrayList<newsItem> result;
//
//        @Override
//        protected void onPreExecute() {
//            pb.setVisibility(View.VISIBLE);
//            super.onPreExecute();
//        }
//
//        @Override
//        protected ArrayList<newsItem> doInBackground(String... params) {
//
//            URL searchUrl = NetworkUtils.buildUrl();
//            ArrayList<newsItem> result = null;
//
//
//            //URL Search = NetworkUtils.buildUrl(searchUrl);
//            String NewsApiSearchResults = null;
//            try {
//
//                NewsApiSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
//                result = openNewsJsonUtil.openNewsJsonUtil(NewsApiSearchResults);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<newsItem> data) {
//            pb.setVisibility(View.INVISIBLE);
//            errorTextView.setVisibility(View.INVISIBLE);
//            if (data != null) {
//                ForecastAdapter adapter = new ForecastAdapter(data, new ForecastAdapter.ItemClickListener() {
//                    @Override
//                    public void onItemClick(int clickedItemIndex) {
//
//                        String url = data.get(clickedItemIndex).getURL();
//                        //   Log.d(TAG, String.format("Url %s", url));
//                        openWebPage(url);
//                    }
//                });
//                rv.setAdapter(adapter);
//            } else {
//                showErrorMessgae();
//            }
//        }
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();

        if (itemNumber == R.id.action_search) {

            LoaderManager loaderManager= getSupportLoaderManager();
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER,null,this).forceLoad();
        } else

        {
            showErrorMessgae();
        }
        return true;
    }

    private void showErrorMessgae() {

        errorTextView.setVisibility(View.VISIBLE);
        rv.setVisibility(View.INVISIBLE);


    }


    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this){

            ArrayList<newsItem> output = null;
            @Override
            protected void onStartLoading() {
            super.onStartLoading();
                ProgressBAR.setVisibility(View.VISIBLE);
            }
            @Override
            public Void loadInBackground(){
                URL searchurl = NetworkUtils.buildUrl();
                try {
                String results = NetworkUtils.getResponseFromHttpUrl(searchurl);


                output = openNewsJsonUtil.openNewsJsonUtil(results);

                db = new DBQuerry(this.getContext()).getWritableDatabase();

                DBMethod.getNews(db, output);


            } catch (Exception e) {
                e.printStackTrace();
            }
return null;
        }
            };
        }//oncreateLoader


    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        ProgressBAR.setVisibility(View.INVISIBLE);

        db= new DBQuerry(MainActivity.this).getReadableDatabase();
        cursor = DBMethod.getAllNewsCursor(db);
        adapter = new ForecastAdapter(cursor, this);
        rv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }

    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_URL));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }
}
