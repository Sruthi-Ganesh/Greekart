package com.example.android.greekart1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 8/1/2017.
 */

public class SearchAdapter extends ArrayAdapter<Search> {

    public Context context;
    String className;
    Double totalPrice;

    public SearchAdapter(Context context, List<Search> search) {

        super(context, 0, search);

        this.context = context;


    }

    public SearchAdapter(Context context, List<Search> search, String className, Double totalPrice) {

        super(context, 0, search);
        this.className = className;
        this.totalPrice = totalPrice;
        this.context = context;
        Log.v("SearchAdapter", className + totalPrice);


    }


    @Override

    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        final Search search = getItem(position);
        TextView defaultView = (TextView) ListItemView.findViewById(R.id.name);
        defaultView.setText(search.getName());
        TextView miwokView = (TextView) ListItemView.findViewById(R.id.summary);
        miwokView.setText(search.getPrice().toString());
        ImageView img = (ImageView) ListItemView.findViewById(R.id.image_item);
        ImageActivity imgActivity = new ImageActivity(search.getImage(), context);
        int resourceId = imgActivity.getImageId();




         ImageButton imgButton = (ImageButton) ListItemView.findViewById(R.id.delete);


        imgButton.setVisibility(View.INVISIBLE);

        Log.v("Class Name",className);

        if (className.equals("CartActivity")) {
            imgButton.setVisibility(View.VISIBLE);
            TextView quantitytText = (TextView) ListItemView.findViewById(R.id.quantityTextView);
            String text = "Quantity: " + search.getQuantity();
            quantitytText.setText(text);

            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DeleteActivity deleteActivity = new DeleteActivity();
                    deleteActivity.delete(search.getColumnName(), search.getName(), context, ItemContract.ItemEntry.TABLE_NAME_CART);


                }
            });
        }
            if(className.equals("WishListActivity"))
            {
                imgButton.setVisibility(View.VISIBLE);
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DeleteActivity deleteActivity = new DeleteActivity();
                        deleteActivity.delete(search.getColumnName(), search.getName(), context, ItemContract.ItemEntry.TABLE_NAME_WISHLIST);


                    }
                });
            }





        img.setImageResource(resourceId);


        return ListItemView;
    }


    public class DeleteActivity extends AppCompatActivity {

        private DatabaseReference mFirebaseDatabase;
        private FirebaseDatabase mFirebaseInstance;

        public void delete(final String key, final String name, final Context context,final String tableName) {

            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("user_info");
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            final String userId = currentFirebaseUser.getUid();

            new AlertDialog.Builder(context)
                    .setTitle("Cancel?")
                    .setMessage("Are you sure you want to delete: " + name)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface arg0, int arg1) {

                            final ProgressDialog pd = new ProgressDialog(context);
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

                            mFirebaseDatabase.child(userId).child(tableName).child(key).removeValue(new DatabaseReference.CompletionListener() {


                                @Override
                                public void onComplete(DatabaseError error, DatabaseReference ref) {
                                    if (error == null) {
                                        Log.v("ItemCursor", "Removed successfully");
                                        Toast.makeText(context.getApplicationContext(), "Deleted!" + name, Toast.LENGTH_LONG).show();
                                        pd.cancel();

                                        if (tableName.equals(ItemContract.ItemEntry.TABLE_NAME_CART)) {

                                            CartActivity cartActivity = new CartActivity(context);


                                        } else if (tableName.equals(ItemContract.ItemEntry.TABLE_NAME_WISHLIST)) {
                                            WishListActivity wish = new WishListActivity(context);
                                        }
                                    }


                                }
                            });
                        }




                    }).create().show();
        }

    }
}
