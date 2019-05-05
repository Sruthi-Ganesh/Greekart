package com.example.android.greekart1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract;
import com.example.android.greekart1.data.ItemContract.ItemEntry;
import com.example.android.greekart1.data.ItemHelper;
import com.example.android.greekart1.email.MailActivity;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.attr.id;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.android.greekart1.R.id.delete;

/**
 * Created by SruthiGanesh on 6/4/2017.
 */

public class ItemCursorAdapter extends CursorAdapter {

    Context context;
    private ItemHelper mDbHelper;
    private String className = "";
    private int i = 0;
    private Uri currentUri = null;

    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ItemCursorAdapter(Context context, Cursor c, String className) {


        super(context, c, 0 /* flags */);
        this.className = className;

    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        this.context = context;
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);


    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {


        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvSummary = (TextView) view.findViewById(R.id.summary);
        ImageView imImage = (ImageView) view.findViewById(R.id.image_item);
        TextView tvQuantity = (TextView) view.findViewById(R.id.quantityTextView);
        ImageButton img = (ImageButton) view.findViewById(delete);
        final deleteActivity delete = new deleteActivity();


        img.setVisibility(View.INVISIBLE);



        if (className.equals("WishListActivity")) {
            img.setVisibility(View.VISIBLE);
            currentUri = ItemEntry.CONTENT_WISHLIST_URI;
        }
        if (className.equals("OrdersActivity")) {
            img.setVisibility(View.VISIBLE);
            currentUri = ItemEntry.CONTENT_ORDERS_URI;
        }


        // Extract properties from cursor
        final String name = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME));
        final String summary = String.valueOf(cursor.getDouble(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PRICE)));
        final String image = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_IMAGE)).trim();

        // Populate fields with extracted properties
        Log.v("Itemcursor", image);
        ImageActivity imgActivity = new ImageActivity(image, context);
        final int resourceId = imgActivity.getImageId();

        imImage.setImageResource(resourceId);

        if (img.getVisibility() == View.VISIBLE) {

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    long id = delete.getId(name, cursor);
                    Uri uri = ContentUris.withAppendedId(currentUri, id);
                    Log.v("Uri", uri.toString());
                    if (className.equals("OrdersActivity")) {
                        delete.deleteOrders(name, uri, cursor, resourceId, Double.parseDouble(summary));
                    } else {


                        delete.delete(uri, name);
                    }



                }
            });

        }


        tvName.setText(name);
        tvSummary.setText("Rs. " + summary);


        try {
            int quantityIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_QUANTITY);
            int quantity = cursor.getInt(quantityIndex);
            tvQuantity.setText("Quantity: " + String.valueOf(quantity));

        } catch (Exception E) {
            Log.v("Item Adapter", "No Quantity");
        }




    }


    public class deleteActivity extends AppCompatActivity {

        public void delete(final Uri uri, final String name) {

            new AlertDialog.Builder(context)
                    .setTitle("Delete?")
                    .setMessage("Are you sure you want to delete: " + name)
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                        public void onClick(DialogInterface arg0, int arg1) {

                            int noofRowsDeleted = context.getContentResolver().delete(uri, null, null);
                            if (noofRowsDeleted > 0) {
                                Toast.makeText(context, "Deleted : " + name, Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).create().show();
        }

        public long getId(String name, Cursor cursor) {

            if(cursor.moveToFirst())
                do {


                    String nameofproduct = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                    int id = cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));
                    Log.v("Uri : ", "name: " + nameofproduct + "id : " + id);
                    if (name.equals(nameofproduct)) {

                        Log.v("Uri : ", "name: " + nameofproduct + "id : " + id);
                        return (long) id;
                    }
                }while(cursor.moveToNext());


            return 0;
        }

        public void deleteOrders(final String name, final Uri uri,final  Cursor cursor, final int resourceId, final Double price) {
            new AlertDialog.Builder(context)
                    .setTitle("Cancel?")
                    .setMessage("Are you sure you want to cancel the order of: " + name)
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
                                        Toast.makeText(context, "Check your internet connection", Toast.LENGTH_SHORT).show();
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

                            final int[] num = {0};


                            if (cursor != null) {
                                final String product_name = cursor.getString(cursor.getColumnIndex(ItemEntry.PRODUCT_NAME));
                                final String user_id = cursor.getString(cursor.getColumnIndex(ItemEntry.USERID));
                                final int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));

                                DatabaseReference mFirebaseDatabase;
                                FirebaseDatabase mFirebaseInstance;
                                mFirebaseInstance = FirebaseDatabase.getInstance();

                                // get reference to 'users' node
                                mFirebaseDatabase = mFirebaseInstance.getReference("users");


                                // store app title to 'app_title' node
                                mFirebaseInstance.getReference("app_title").setValue("Realtime Database");


                                mFirebaseDatabase.child(user_id).child("Product").child(product_name).removeValue(new DatabaseReference.CompletionListener() {


                                    @Override
                                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                                        if (error == null) {
                                            Log.v("ItemCursor", "Removed successfully");
                                            pd.cancel();
                                            Log.v("ItemCursor", user_id +
                                                    " " + product_name);
                                            Toast.makeText(context, "Deleted : " + name, Toast.LENGTH_SHORT).show();

                                            num[0] = 1;
                                            deleteOrder(uri, name);

                                            Intent intent = new Intent(context.getApplicationContext(), CancellationActivity.class);
                                            intent.putExtra("Product", product_name);
                                            intent.putExtra("Image", resourceId);
                                            intent.putExtra("Quantity", quantity);
                                            intent.putExtra("Price", price);
                                            context.startActivity(intent);


                                        }
                                    }


                                });


                            }


                        }

                    }).create().show();

        }

        public void calculatecost(Cursor cursor)
        {
            double totalPrice = 0.00;


            if (cursor.moveToFirst())
            {
                do
                {
                    double price = cursor.getDouble(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE));
                    int quantity = cursor.getInt(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY));
                    totalPrice = totalPrice + (price * quantity);
                }while(cursor.moveToNext());

            }
            CostActivity cost= (CostActivity) context;
            cost.setCost(totalPrice);



        }

        public void deleteOrder(final Uri uri, final String name)
        {
            int noofRowsDeleted = context.getContentResolver().delete(uri, null, null);
            if (noofRowsDeleted > 0) {
                Toast.makeText(context, "Deleted : " + name, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface CostActivity
    {
       void setCost(Double totalPrice);



    }
}


