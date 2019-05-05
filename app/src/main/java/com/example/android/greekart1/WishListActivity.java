package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.LoaderManager;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by SruthiGanesh on 6/17/2017.
 */

public class WishListActivity extends AppCompatActivity{

    private String userId;
    ArrayList<Uri> uris = new ArrayList<>();
    ArrayList<Search> searches = new ArrayList<>();
    Context context;
    ProgressDialog pd;

    WishListActivity(final Context context) {
        this.context = context;

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userId = currentFirebaseUser.getUid();
        getFirebaseData();

        pd = new ProgressDialog(context);


        pd.setMessage("Loading! Please wait");
        pd.show();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (pd.isShowing()) {
                    pd.dismiss();
                    Toast.makeText(context.getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
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


    }


    public void getFirebaseData() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_info").child(userId).child(ItemEntry.TABLE_NAME_WISHLIST);
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot

                        collectData(dataSnapshot);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }



    private void collectData(DataSnapshot dataSnapshot) {

        Double totalPrice = 0.00;
        //iterate through each user, ignoring their UID
        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
            String key =  messageSnapshot.getKey();
            String name = messageSnapshot.child("name").getValue().toString();
            String price = messageSnapshot.child("price").getValue().toString();
            String quantity = messageSnapshot.child("quantity").getValue().toString();
            String image = messageSnapshot.child("image").getValue().toString();
            String uri = messageSnapshot.child("uri").getValue().toString();
            searches.add(new Search(name, image, uri, Integer.parseInt(quantity), Double.parseDouble(price),key));
            totalPrice += Double.parseDouble(price);

            uris.add(Uri.parse(uri));




            Log.v("Searches", searches.toString());





        }
        pd.cancel();
        Intent intent = new Intent(context.getApplicationContext(), SearchResultsActivity.class);
        intent.putExtra("Search", searches);
        intent.putExtra("Uri",uris);
        intent.putExtra("TotalPrice", totalPrice);
        intent.putExtra("ClassName", getClass().getSimpleName());
        context.startActivity(intent);
    }
}
