package com.example.android.greekart1;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.greekart1.data.ItemContract.ItemEntry;

import com.example.android.greekart1.data.ItemContract;
import com.example.android.greekart1.data.ItemHelper;

import static com.example.android.greekart1.R.id.delete;

/**
 * Created by SruthiGanesh on 8/5/2017.
 */

public class UserAdapter extends CursorAdapter {

    Context contexts;
    private String className="";
    private int  i= 0;


    /**
     * Constructs a new {@link ItemCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public UserAdapter(Context context, Cursor c, String className) {


        super(context, c, 0);
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

        this.contexts = context;
        return LayoutInflater.from(context).inflate(R.layout.details_activity, parent, false);



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


        TextView tvName = (TextView) view.findViewById(R.id.table_name);
        TextView tvSummary = (TextView) view.findViewById(R.id.table_address);


        // Extract properties from cursor
        final String name = cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME)).trim();

        final String address = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_ADDRESS)).trim();

        Button checkout = (Button) view.findViewById(R.id.deliver);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setTitle("Order?")
                        .setMessage("By clicking yes your order is confirmed")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                            public void onClick(DialogInterface arg0, int arg1) {

                OnClickInAdapter onClickInAdapter = (OnClickInAdapter) context;
                onClickInAdapter.onClickInAdapter(name,address);
                Toast.makeText(context,"Clicked",Toast.LENGTH_SHORT).show();

                            }
                        }).create().show();

            }
        });

        // Populate fields with extracted properties
       ImageButton img = (ImageButton) view.findViewById(R.id.delete_table);





            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Delete?")
                            .setMessage("Are you sure you want to delete: " + name)
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {


                                public void onClick(DialogInterface arg0, int arg1) {
                                    deleteActivity delete = new deleteActivity();
                                    long id = delete.getId(name,cursor);
                                    Uri uri = ContentUris.withAppendedId(ItemEntry.CONTENT_USER_URI, id);
                                    Log.v("Uri" , uri.toString());

                                    delete.delete(uri,name);

                                }
                            }).create().show();



                }
            });





        tvName.setText(name);
        tvSummary.setText(address);





    }


    public interface OnClickInAdapter {
       void onClickInAdapter(String name,String address);
    }




    public class deleteActivity extends AppCompatActivity {

        public void delete(Uri uri,String name)
        {

            int noofRowsDeleted = contexts.getContentResolver().delete(uri,null,null);
            if(noofRowsDeleted>0)
            {
                Toast.makeText(contexts, "Deleted : " + name, Toast.LENGTH_SHORT).show();
            }

        }

        public long getId(String name,Cursor cursor)
        {

            if(cursor.moveToFirst())
            {
                do {
                    String nameofproduct = cursor.getString(cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME));
                    int id =cursor.getInt(cursor.getColumnIndex(ItemEntry._ID));
                    Log.v("Uri : " , "name: " + nameofproduct + "id : " + id);
                    if(name.equals(nameofproduct))
                    {

                        Log.v("Uri : " , "name: " + nameofproduct + "id : " + id);
                        return (long) id;
                    }


                }while(cursor.moveToNext());
            }
            return 0;
        }
    }
}