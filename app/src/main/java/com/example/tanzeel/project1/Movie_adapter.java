package com.example.tanzeel.project1;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Movie_adapter extends BaseAdapter
{
    Context mContext;
    String LOG_CAT="ef";
    public Movie_adapter(Context c) {
        mContext = c;
    }
    @Override
    public int getCount() {
        return MainActivity.image_uri.size();
    }

    public  void setGridData() {
       // this.mGridData = as;
        notifyDataSetChanged();
       // Log.v(LOG_CAT, "in set griddata");
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null)
        {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(300,300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else
        {
            imageView = (ImageView) convertView;
        }
        Picasso.with(mContext).load(MainActivity.image_uri.get(position)).into(imageView);
        return imageView;
    }
}
