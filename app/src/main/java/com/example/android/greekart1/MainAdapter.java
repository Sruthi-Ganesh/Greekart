package com.example.android.greekart1;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.ContentUris.withAppendedId;
import static java.security.AccessController.getContext;

/**
 * Created by SruthiGanesh on 6/15/2017.
 */

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<String> mNameSet;
    private ArrayList<String> imageSet;
    private ArrayList<Double> priceSet;
    private Context context;
    private Uri currentUri = null;
    private int lastIndex = 0;


    public MainAdapter(ArrayList<String> mDataSet, ArrayList<String> mImageSet, ArrayList<Double> mPriceSet, Context context,Uri uri, int lastitem)
    {
        mNameSet=mDataSet;
        imageSet=mImageSet;
        priceSet=mPriceSet;
        this.context = context;
        currentUri = uri;
        lastIndex = lastitem;
    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);

       context =  v.getContext();

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, final int position) {

        holder.mTitle.setText(mNameSet.get(position));

        int image = new ImageActivity(imageSet.get(position),context).getImageId();
        holder.mImage.setImageResource(image);
        holder.mPrice.setText("Rs. " + String.valueOf(priceSet.get(position)));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri  = ContentUris.withAppendedId(currentUri , (long) (lastIndex-position));
                Log.v("Uri: ", uri + " " + position);
                new intentCaller().callIntent(uri);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mNameSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView mTitle;
        public TextView mPrice;
        public ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.title_recycler_view);
            mPrice = (TextView) itemView.findViewById(R.id.price_recycler_view);
            mImage = (ImageView) itemView.findViewById(R.id.image_view_recycler);



        }
    }


    public class intentCaller extends AppCompatActivity
    {
        public void callIntent(Uri uri)
        {
            Intent intent = new Intent(context, ProductDescription.class);
            intent.setData(uri);
            context.startActivity(intent);
        }
    }
}
