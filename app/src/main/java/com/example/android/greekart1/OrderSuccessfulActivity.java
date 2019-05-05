package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract;
import com.example.android.greekart1.data.ItemContract.ItemEntry;
import com.example.android.greekart1.data.ItemHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.android.greekart1.R.drawable.i;

/**
 * Created by SruthiGanesh on 7/30/2017.
 */

public class OrderSuccessfulActivity extends AppCompatActivity  {

    ItemCursorAdapter itemCursorAdapter;
    private static final int ITEM_LOADER = 0;
    private String userID;
    ItemHelper mDbHelper;
    SearchAdapter arrayAdapter;
    ProgressDialog pd;
    ArrayList<Search> searches = new ArrayList<>();
    private DatabaseReference mFirebaseDatabase;
 SharedPreference sharedPreference;
    private FirebaseDatabase mFirebaseInstance;

   String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);

        userID = getIntent().getStringExtra("UserId");
        sharedPreference = new SharedPreference(this);
        sharedPreference.sharedPreferenceNumber();











        mFirebaseInstance = FirebaseDatabase.getInstance();


        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("user_info");
        getFirebaseData();
        dataSnapShot();



        // store app title to 'app_title' node

    }

    public void dataSnapShot() {



        Query lastQuery = mFirebaseDatabase.child(userid).child(ItemEntry.TABLE_NAME_ORDERS).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {

                    sharedPreference.putSharedPreferenceOrder(Integer.parseInt(objSnapshot.getKey()));

                    Log.v("Product", objSnapshot.getKey());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getFirebaseData() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        userid= currentFirebaseUser.getUid();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_info").child(userid).child(ItemContract.ItemEntry.TABLE_NAME_CART);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        collectData(dataSnapshot,userid);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }



    private void collectData(DataSnapshot dataSnapshot, final String userid) {



        pd = new ProgressDialog(OrderSuccessfulActivity.this);


        pd.setMessage("Loading! Please wait");
        pd.show();

        final Handler handler = new Handler();
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
        //iterate through each user, ignoring their UID
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {



            String key =  messageSnapshot.getKey();
            String name = messageSnapshot.child("name").getValue().toString();
            String price = messageSnapshot.child("price").getValue().toString();
            String quantity = messageSnapshot.child("quantity").getValue().toString();
            String image = messageSnapshot.child("image").getValue().toString();
            String uri = messageSnapshot.child("uri").getValue().toString();
            searches.add(new Search(name, image, uri, Integer.parseInt(quantity), Double.parseDouble(price),key));

            Log.v("Searches",name +price + quantity + image + uri);

            Cart cart = new Cart(name,Double.parseDouble(price),image,Integer.parseInt(quantity),uri);

            int i = sharedPreference.getSharedPreferenceOrder() + 1;

            mFirebaseDatabase.child(userid).child(ItemEntry.TABLE_NAME_ORDERS).child(String.valueOf(i)).setValue(cart);


            ListView listView = (ListView) findViewById(R.id.list_view_successful) ;

            arrayAdapter = new SearchAdapter(OrderSuccessfulActivity.this, searches,getClass().getSimpleName(),0.00);

            listView.setAdapter(arrayAdapter);






        }



        mFirebaseDatabase.child(userid).child(ItemEntry.TABLE_NAME_CART).removeValue();
        pd.cancel();







        }







    @Override
    public void onBackPressed() {



        new AlertDialog.Builder(OrderSuccessfulActivity.this)
                .setTitle("Exit?")
                .setMessage("Do you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface arg0, int arg1) {


                        Intent intent= new Intent(OrderSuccessfulActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                }).create().show();


    }
}
