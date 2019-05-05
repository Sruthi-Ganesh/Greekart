package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SruthiGanesh on 8/1/2017.
 */

public class SearchResultsActivity extends AppCompatActivity {
    SearchAdapter arrayAdapter;
    Toolbar toolbar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    ProgressDialog pd = null;
    ListView listView;
    String userId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String className = getIntent().getStringExtra("ClassName");

        Log.v("Class Name",": " + className);





        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("user_info");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        userId = currentFirebaseUser.getUid();



        if(className.equals("CartActivity"))
        {
            setContentView(R.layout.activity_checkout);
            toolbar = (Toolbar) findViewById(R.id.toolbar_activity);

            toolbar.setTitle(className);



            toolbar.inflateMenu(R.menu.cart_bar);
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
                            new AlertDialog.Builder(SearchResultsActivity.this)
                                    .setTitle("Delete?")
                                    .setMessage("Do you want to delete all the items in cart?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                                        public void onClick(DialogInterface arg0, int arg1) {
                                            deleteCart();
                                        }
                                    }).create().show();

                            return true;
                        case R.id.search:
                            search();
                            return true;


                    }
                    return SearchResultsActivity.super.onOptionsItemSelected(item);
                }






            });
            View empty_view = findViewById(R.id.empty_view);
            ListView listView = (ListView)findViewById(R.id.list_view_item);

            listView.setEmptyView(empty_view);


        }
        else if(className.equals("WishListActivity"))
        {
            setContentView(R.layout.activity_wishlist);
            toolbar = (Toolbar) findViewById(R.id.toolbar_activity);


            toolbar.inflateMenu(R.menu.wishlist_bar);
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
                        case R.id.action_wish:
                            new AlertDialog.Builder(SearchResultsActivity.this)
                                    .setTitle("Delete?")
                                    .setMessage("Do you want to delete all the items in wishlist?")
                                    .setNegativeButton(android.R.string.no, null)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                                        public void onClick(DialogInterface arg0, int arg1) {
                                            deleteWish();
                                        }
                                    }).create().show();
                            return true;
                        case R.id.search:
                            search();
                            return true;


                    }
                    return SearchResultsActivity.super.onOptionsItemSelected(item);
                }






            });

            toolbar.setTitle(className);

            View empty_view = findViewById(R.id.empty_view);
            ListView listView = (ListView)findViewById(R.id.list_view_item);

            listView.setEmptyView(empty_view);

        }
        else {
            setContentView(R.layout.activity_main);
            toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
            toolbar.setTitle(getIntent().getStringExtra("Text"));
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
                    return SearchResultsActivity.super.onOptionsItemSelected(item);
                }






            });
        }


        pd = new ProgressDialog(SearchResultsActivity.this);





        final List<Search> searches = getIntent().getParcelableArrayListExtra("Search");

        final ArrayList<Uri> uri = getIntent().getParcelableArrayListExtra("Uri");
        final ArrayList<Integer> quantities = getIntent().getIntegerArrayListExtra("Quantity");

        final Double totalPrice = getIntent().getDoubleExtra("TotalPrice",0.00);


        Log.v("Search Results Activity", uri + "");
        if(className.equals("CartActivity"))
        {
            arrayAdapter = new SearchAdapter(SearchResultsActivity.this, searches,className,totalPrice);

            TextView text = (TextView) findViewById(R.id.total_price_text);
            text.setText(totalPrice.toString());

            Button proceedtocheckout = (Button) findViewById(R.id.proceedtocheckout);
            proceedtocheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(searches==null)
                    {
                        Toast.makeText(getApplicationContext(),"No items in the cart",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(getApplicationContext(),FinalCheckOutActivity.class);
                    intent.putExtra("TotalPrice",totalPrice);
                    startActivity(intent);
                }
            });

        }
        else if(className.equals("WishListActivity"))
        {
            arrayAdapter = new SearchAdapter(SearchResultsActivity.this, searches,className,totalPrice);
        }
        else {

            arrayAdapter = new SearchAdapter(SearchResultsActivity.this, searches,className,totalPrice);
        }
        ListView listView = (ListView)findViewById(R.id.list_view_item);



        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(uri!=null)
                {
                    Intent intent = new Intent(getApplicationContext(), ProductDescription.class);



                    intent.setData(uri.get((int) id));
                    if(className.equals("CartActivity"))
                    {
                        intent.putExtra("Quantity",quantities.get((int) id));
                    }

                    startActivity(intent);
                }
            }
        });




}

public void deleteCart()
{

    pd.setMessage("Loading! Please wait");
    pd.show();

    final Handler handler  = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (pd.isShowing()) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    };

    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            handler.removeCallbacks(runnable);

        }
    });

    handler.postDelayed(runnable, 20000);
    mFirebaseDatabase.child(userId).child(ItemContract.ItemEntry.TABLE_NAME_CART).removeValue(new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            pd.cancel();
            Toast.makeText(getApplicationContext(),"Cart is Cleared", Toast.LENGTH_SHORT).show();
            new CartActivity(SearchResultsActivity.this);

        }
    });
}

public  void deleteWish()
{
    pd.setMessage("Loading! Please wait");
    pd.show();

    final Handler handler  = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (pd.isShowing()) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    };

    pd.setOnDismissListener(new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            handler.removeCallbacks(runnable);

        }
    });

    handler.postDelayed(runnable, 20000);
    mFirebaseDatabase.child(userId).child(ItemContract.ItemEntry.TABLE_NAME_WISHLIST).removeValue(new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

            pd.cancel();
            Toast.makeText(getApplicationContext(),"Wishlist is Cleared", Toast.LENGTH_SHORT).show();
            new WishListActivity(SearchResultsActivity.this);

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



}
