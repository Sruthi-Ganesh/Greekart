package com.example.android.greekart1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.android.greekart1.data.ItemContract.ItemEntry;


import com.example.android.greekart1.data.ItemHelper;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 6/4/2017.
 */

public class BabyCareActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ItemHelper mDbHelper;
    ItemCursorAdapter itemCursorAdapter;
    private int ITEM_LOADER =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ListView ItemListView = (ListView) findViewById(R.id.list_view_item);

        mDbHelper = new ItemHelper(getApplicationContext());
        itemCursorAdapter = new ItemCursorAdapter(this, null,getClass().getSimpleName());
        ItemListView.setAdapter(itemCursorAdapter);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);
        ItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(BabyCareActivity.this,ProductDescription.class);
                Uri uri = ContentUris.withAppendedId(ItemEntry.CONTENT_BABYCARE_URI,id);
                Log.v("BREAK FAST ACTIVITY",  uri + "");

                intent.setData(uri);

                startActivity(intent);


            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Baby Care");



        toolbar.inflateMenu(R.menu.menu_bar);
        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_cart:
                        showCart();
                        return true;
                    case R.id.search:
                        search();
                        return true;


                }
                return BabyCareActivity.super.onOptionsItemSelected(item);
            }






        });

    }


    public void showCart()
    {
        CartActivity cart = new CartActivity(this);
    }


   public void search()
   {
       Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
       startActivity(intent);
   }









    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String projection[] = {ItemEntry._ID,ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_PRICE,ItemEntry.COLUMN_ITEM_IMAGE};

        return new CursorLoader(this,ItemEntry.CONTENT_BABYCARE_URI,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        itemCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        itemCursorAdapter.swapCursor(null);

    }


}
