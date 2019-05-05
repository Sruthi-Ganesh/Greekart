package com.example.android.greekart1;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract.ItemEntry;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentUris.withAppendedId;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.android.greekart1.R.drawable.a;
import static com.example.android.greekart1.R.id.searchView;

/**
 * Created by SruthiGanesh on 6/13/2017.
 */


public class SearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<String> arrayName;
    SearchAdapter arrayAdapter;
    ArrayAdapter<String> adapter;
    private SearchView searchview;
    ArrayList<Uri> new_uri;
    private int ITEM_LOADER = 0;
    private String text;
    ArrayList<Search> Searches = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list_view);
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
        new_uri = new ArrayList<>();
        Searches.clear();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry.COLUMN_ITEM_NAME};

        return new CursorLoader(this, ItemEntry.CONTENT_URI, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        searchview = (SearchView) findViewById(R.id.searchView);
        searchview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchview.setIconified(false);
            }
        });

        arrayName = new ArrayList<>();


        arrayName.add("pickle");
        arrayName.add("Atta");
        arrayName.add("Mutton Masala");
        arrayName.add("Chilli Powder");
        arrayName.add("Sambar Powder");
        arrayName.add("Semiya");
        arrayName.add("Salt");
        arrayName.add("Baking Soda");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayName);


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String query) {

                changedText(query);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                adapter.getFilter().filter(newText);

                return true;
            }
        });

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.clear();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu item) {
        searchview.clearFocus();
        searchview.setIconified(false);

        return true;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void changedText(String text) {

       this.text = text;

       onTextSubmit();
        if (Searches.size() <= 0) {
            Toast.makeText(this, "No product available for this search", Toast.LENGTH_SHORT).show();
            return;

        }

        Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
        intent.putExtra("Search", Searches);
        intent.putExtra("Text", text);
        intent.putExtra("ClassName", getClass().getSimpleName());
        intent.putExtra("Uri", new_uri);
        startActivity(intent);


        Toast.makeText(getApplicationContext(), "Clicked: " + text, Toast.LENGTH_SHORT).show();


           /* }
        });*/
    }

    public void onTextSubmit() {
        new_uri.clear();


        onTextReturn(ItemEntry.CONTENT_BABYCARE_URI);
        onTextReturn(ItemEntry.CONTENT_BREAKFAST_URI);
        onTextReturn(ItemEntry.CONTENT_DIRECTFROMFARMS_URI);
        onTextReturn(ItemEntry.CONTENT_DRINKS_URI);
        onTextReturn(ItemEntry.CONTENT_DRY_FRUITS_URI);
        onTextReturn(ItemEntry.CONTENT_EDIBLE_OILS_URI);
        onTextReturn(ItemEntry.CONTENT_FRAGRANCE_URI);
        onTextReturn(ItemEntry.CONTENT_HEALTH_DRINKS_URI);
        onTextReturn(ItemEntry.CONTENT_HEALTH_URI);
        onTextReturn(ItemEntry.CONTENT_URI);
        onTextReturn(ItemEntry.CONTENT_HOMENEEDS_URI);
        onTextReturn(ItemEntry.CONTENT_PERSONALCARE_LIST);
        onTextReturn(ItemEntry.CONTENT_TEA_COFFEE_URI);



    }

    public void onTextReturn(Uri new_uris) {
        Cursor cursor = getContentResolver().query(new_uris, null, null, null, null);
        int i = 0;

        if (cursor.moveToFirst()) {
            do {
                {
                    String name = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                    boolean ifexist = name.toLowerCase().contains(text.toLowerCase());
                    if (ifexist) {

                        String image = (cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE)));
                        Double price = (cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE)));
                        int id = cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));

                        Uri uris = ContentUris.withAppendedId(new_uris, id);
                        new_uri.add(uris);

                        String uri = uris.toString();
                        Log.v("Search Activity", uri + " * " + new_uri);
                        Searches.add(new Search(name, image, uri, 20, price,"X"));


                    }

                }
                i++;
            } while (cursor.moveToNext());
        }
    }
}
