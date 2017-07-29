package com.example.astatic.newsapp.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.database.Cursor;
import com.squareup.picasso.Picasso;
import com.example.astatic.newsapp.R;
import com.example.astatic.newsapp.utilities.Database.DBTable;
import java.util.ArrayList;

/**
 * Created by static on 6/28/17.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ItemHolder> {

    private ArrayList<newsItem> data;
    ItemClickListener listener;
    Context context;
    private Cursor cursor;

    public ForecastAdapter( Cursor cursor, ItemClickListener listener) {
      this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.list_items, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder,position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }


    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tView;
        TextView information;
        TextView date;
        String published;
        String article;
        String asc1;
        String url;
        ImageView im;


        ItemHolder(View view) {
            super(view);
            tView = (TextView) view.findViewById(R.id.title);
            information = (TextView) view.findViewById(R.id.description);
            date = (TextView) view.findViewById(R.id.date);
            im=(ImageView)view.findViewById(R.id.image_view);

            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            article = cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_Title));
            asc1 = cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_DESC));
            url = cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_URL));
            published = cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_PUB));
            String imgurl=cursor.getString(cursor.getColumnIndex(DBTable.TABLE_ARTICLES.COL_NEWS_IMG));

            tView.setText(article);
            information.setText(asc1);
            date.setText(published);
            Picasso.with(context).load(imgurl).into(im);

        }



        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
        }
    }
}