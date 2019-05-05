package com.example.android.greekart1;

import android.app.LoaderManager;
import android.content.ClipData;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.greekart1.Internet.InternetActivity;
import  com.example.android.greekart1.data.ItemContract.ItemEntry;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.alirezaafkar.toolbar.RtlActionBarDrawerToggle;
import com.alirezaafkar.toolbar.RtlToolbar;
import com.example.android.greekart1.data.ItemContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_BABYCARE_URI;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar toolbar;
    private NavigationDrawerFragment drawerFragment;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> mDataSet;

    private int ITEM_LOADER=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("GreeKart");
        toolbar.setPadding(0,0,0,0);

        InternetActivity internetActivity = new InternetActivity(MainActivity.this);
        boolean connected = internetActivity.checkConnection();

        showSnack(connected);
        if(!connected)
        {
            return;
        }





        //Setup FAB to open EditorActivity


        Button viewAllGroceries = (Button) findViewById(R.id.button_view_groceries);
        viewAllGroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, GroceryActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllBabyCare = (Button) findViewById(R.id.button_view_babycare);
        viewAllBabyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BabyCareActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllBreakFast = (Button) findViewById(R.id.button_view_breakfast);
        viewAllBreakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BreakFastActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllDirect = (Button) findViewById(R.id.button_view_direct);
        viewAllDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DirectFromFarmsActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllDrinks = (Button) findViewById(R.id.button_view_drinks);
        viewAllDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DrinksActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllDryFruits = (Button) findViewById(R.id.button_view_dryfruits);
        viewAllDryFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, DryFruitsActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllEdible = (Button) findViewById(R.id.button_view_edible);
        viewAllEdible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, EdibleOilsActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllFragrance = (Button) findViewById(R.id.button_view_fragrance);
        viewAllFragrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FragranceActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllHealth = (Button) findViewById(R.id.button_view_healthcare);
        viewAllHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HealthCareActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllHealthDrinks = (Button) findViewById(R.id.button_view_healthdrinks);
        viewAllHealthDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HealthDrinksList.class);
                startActivity(intent);

            }
        });
        Button viewAllHomeNeeds = (Button) findViewById(R.id.button_view_homeneeds);
        viewAllHomeNeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HomeNeedsActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllHouseHolds = (Button) findViewById(R.id.button_view_household);
        viewAllHouseHolds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, HouseHoldActivity.class);
                startActivity(intent);

            }
        });
        Button viewAllPersonalCare = (Button) findViewById(R.id.button_view_personalcare);
        viewAllPersonalCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, PersonalCareActivity.class);
                startActivity(intent);

            }
        });
        Button TeaCoffee = (Button) findViewById(R.id.button_view_teacoffee);
        TeaCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, TeaCoffeeActivity.class);
                startActivity(intent);

            }
        });





        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_nav);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment.setUp(R.id.fragment_nav, drawerLayout, toolbar);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout_mycart);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity cart = new CartActivity(MainActivity.this);
                cart.startActivity();
            }
        });

        LinearLayout linearLayoutWishList = (LinearLayout) findViewById(R.id.linearlayout_mywishlist);
        linearLayoutWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WishListActivity wish = new WishListActivity(MainActivity.this);
            }
        });

        LinearLayout linearLayoutBreakFast = (LinearLayout) findViewById(R.id.linearlayout_breakfast);
        linearLayoutBreakFast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BreakFastActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutBabyCare = (LinearLayout) findViewById(R.id.linearlayout_babycare);
        linearLayoutBabyCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BabyCareActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutDirect = (LinearLayout) findViewById(R.id.linearlayout_directfromfarms);
        linearLayoutDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DirectFromFarmsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutDrinks = (LinearLayout) findViewById(R.id.linearlayout_drinks);
        linearLayoutDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DrinksActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutDryFruits = (LinearLayout) findViewById(R.id.linearlayout_dryfruits);
        linearLayoutDryFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),DryFruitsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutEdibleOils = (LinearLayout) findViewById(R.id.linearlayout_edibleoils);
        linearLayoutEdibleOils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EdibleOilsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutFragrance = (LinearLayout) findViewById(R.id.linearlayout_fragrance);
        linearLayoutFragrance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FragranceActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutgroceries = (LinearLayout) findViewById(R.id.linearlayout_groceries);
        linearLayoutgroceries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GroceryActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutHealthcare = (LinearLayout) findViewById(R.id.linearlayout_health_care);
        linearLayoutHealthcare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HealthCareActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutHealthDrinks = (LinearLayout) findViewById(R.id.linearlayout_health_drinks);
        linearLayoutHealthDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HealthDrinksList.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayoutAccount = (LinearLayout) findViewById(R.id.linearlayout_myaccount);
        linearLayoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutOrders = (LinearLayout) findViewById(R.id.linearlayout_myorders);
        linearLayoutOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               OrdersActivity orders = new OrdersActivity(MainActivity.this);
            }
        });
        LinearLayout linearLayoutHomeNeeds= (LinearLayout) findViewById(R.id.linearlayout_home_needs);
        linearLayoutHomeNeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeNeedsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutHouseHold= (LinearLayout) findViewById(R.id.linearlayout_HouseHold);
        linearLayoutHouseHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HouseHoldActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutPersonalCare= (LinearLayout) findViewById(R.id.linearlayout_personal_care);
        linearLayoutPersonalCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PersonalCareActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout linearLayoutTeaCoffee= (LinearLayout) findViewById(R.id.linearlayout_teacoffee);
        linearLayoutTeaCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TeaCoffeeActivity.class);
                startActivity(intent);
            }
        });


















    }

    private void showSnack(boolean isConnected) {
        String message = " ";

        if (!isConnected) {

            message = "Sorry! Not connected to internet";
            Toast.makeText(this, message,Toast.LENGTH_SHORT).show();


        }


    }



    public void showCart()
    {
        CartActivity cart = new CartActivity(this);
        cart.startActivity();

    }



    public void search()
    {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_barl file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_cart:
                showCart();
                return true;
            case R.id.search:
                search();
                return true;
            case R.id.wishlist:
                wishList();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void wishList()
    {
        WishListActivity wish = new WishListActivity(MainActivity.this);
    }

    public void adapterView(int id,Uri uri)
    {
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        mRecyclerView = (RecyclerView) findViewById(id);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDataSet = new ArrayList<>();
        ArrayList<String> mImageSet = new ArrayList<>();
        ArrayList<Double> mPriceSet = new ArrayList<>();
        int count = 0;
        int index=0;


        if(cursor.moveToLast())
        {
            index = cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));

            do {
                String name = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                Double price = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                String image = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE));
                mDataSet.add(name);
                mImageSet.add(image);
                mPriceSet.add(price);
                count ++;
            }while(cursor.moveToPrevious() && count<5);
        }

        mAdapter = new MainAdapter(mDataSet,mImageSet,mPriceSet,getApplicationContext(),uri,index);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String projection[] = {ItemEntry.COLUMN_ITEM_NAME,ItemEntry.COLUMN_ITEM_IMAGE, ItemEntry.COLUMN_ITEM_PRICE};

        return new CursorLoader(this,ItemEntry.CONTENT_URI,projection,null,null,null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {



        adapterView(R.id.recycler_view,ItemEntry.CONTENT_URI);

        adapterView(R.id.recycler_view1,ItemEntry.CONTENT_BABYCARE_URI);
        adapterView(R.id.recycler_view2,ItemEntry.CONTENT_BREAKFAST_URI);
        adapterView(R.id.recycler_view3,ItemEntry.CONTENT_DIRECTFROMFARMS_URI);
        adapterView(R.id.recycler_view4,ItemEntry.CONTENT_DRINKS_URI);
        adapterView(R.id.recycler_view5,ItemEntry.CONTENT_DRY_FRUITS_URI);
        adapterView(R.id.recycler_view6,ItemEntry.CONTENT_EDIBLE_OILS_URI);
        adapterView(R.id.recycler_view7,ItemEntry.CONTENT_FRAGRANCE_URI);
        adapterView(R.id.recycler_view8,ItemEntry.CONTENT_HEALTH_URI);
        adapterView(R.id.recycler_view9,ItemEntry.CONTENT_HEALTH_DRINKS_URI);
        adapterView(R.id.recycler_view10,ItemEntry.CONTENT_HOMENEEDS_URI);
        adapterView(R.id.recycler_view11,ItemEntry.CONTENT_HOMEHOLD_URI);
        adapterView(R.id.recycler_view12,ItemEntry.CONTENT_PERSONALCARE_LIST);
        adapterView(R.id.recycler_view13,ItemEntry.CONTENT_TEA_COFFEE_URI);









    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDataSet.clear();
        mRecyclerView.clearFocus();
    }

}
