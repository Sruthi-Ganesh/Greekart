package com.example.android.greekart1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.android.greekart1.R;
import com.example.android.greekart1.data.ItemContract.ItemEntry;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.content.ContentUris.parseId;
import static android.drm.DrmStore.DrmObjectType.CONTENT;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_BABYCARE_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_BREAKFAST_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_CART_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_DIRECTFROMFARMS_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_DRINKS_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_HOMENEEDS_URI;
import static com.example.android.greekart1.data.ItemContract.ItemEntry.CONTENT_ORDERS_URI;
import static com.example.android.greekart1.data.ItemHelper.LOG_TAG;

/**
 * Created by SruthiGanesh on 6/4/2017.
 */

public class ItemProvider extends ContentProvider {

    private ItemHelper mDbHelper;

    private static final int ITEMS = 100;

    private static final int ITEMS_ID = 101;
    private static Uri CONTENT_URI = null;
    private static Uri CONTENT_BABYCARE_URI = null;
    private static Uri CONTENT_BREAKFAST_URI = null;
    private static Uri CONTENT_DIRECTFROMFARMS_URI = null;
    private static Uri CONTENT_DRINKS_URI = null;
    private static Uri CONTENT_DRY_FRUITS_URI = null;
    private static Uri CONTENT_EDIBLE_OILS_URI = null;
    private static Uri CONTENT_FRAGRANCE_URI = null;
    private static Uri CONTENT_HEALTH_CARE_URI = null;
    private static Uri CONTENT_HEALTH_DRINKS_URI = null;
    private static Uri CONTENT_ORDERS_URI = null;
    private static Uri CONTENT_HOMENEEDS_URI = null;
    private static Uri CONTENT_HOUSEHOLD_URI = null;
    private static Uri CONTENT_PERSONALCARE_URI = null;
    private static Uri CONTENT_TEACOFFEE_URI = null;




    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


    static {
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ITEMS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_CART, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_CART + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_WISH, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_WISH + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BREAKFAST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BREAKFAST, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BABY + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_BABY, ITEMS);

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DIRECT_FARMS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DIRECT_FARMS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DRY_FRUITS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DRY_FRUITS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_EDIBLE_OILS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_EDIBLE_OILS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_FRAGRANCE + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_FRAGRANCE, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HEALTH_CARE + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HEALTH_CARE, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HEALTH_DRINKS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HEALTH_DRINKS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DRINKS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_DRINKS, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ORDERS_LIST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_ORDERS_LIST, ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_USER_LIST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_USER_LIST ,ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HOME_NEEDS + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HOME_NEEDS ,ITEMS);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HOUSEHOLD_LIST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_HOUSEHOLD_LIST ,ITEMS);

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_PERSONALCARE_LIST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_PERSONALCARE_LIST ,ITEMS);

        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_TEACOFFEE_LIST + "/#", ITEMS_ID);
        sUriMatcher.addURI(ItemContract.CONTENT_AUTHORITY, ItemContract.PATH_TEACOFFEE_LIST ,ITEMS);




    }

    @Override
    public boolean onCreate() {
        mDbHelper = new ItemHelper(this.getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();

        String tableName = "";
        Cursor cursor = null;
        try {


            long id = ContentUris.parseId(uri);
            String path = "" + id;
            CONTENT_BABYCARE_URI = Uri.withAppendedPath(ItemEntry.CONTENT_BABYCARE_URI, path);
            CONTENT_URI = Uri.withAppendedPath(ItemEntry.CONTENT_URI, path);
            CONTENT_BREAKFAST_URI = Uri.withAppendedPath(ItemEntry.CONTENT_BREAKFAST_URI, path);
            CONTENT_DIRECTFROMFARMS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_DIRECTFROMFARMS_URI, path);
            CONTENT_DRINKS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_DRINKS_URI, path);
            CONTENT_DRY_FRUITS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_DRY_FRUITS_URI, path);

            CONTENT_EDIBLE_OILS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_EDIBLE_OILS_URI, path);
            CONTENT_FRAGRANCE_URI = Uri.withAppendedPath(ItemEntry.CONTENT_FRAGRANCE_URI, path);
            CONTENT_HEALTH_CARE_URI = Uri.withAppendedPath(ItemEntry.CONTENT_HEALTH_URI, path);
            CONTENT_HEALTH_DRINKS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_HEALTH_DRINKS_URI, path);
            CONTENT_ORDERS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_ORDERS_URI, path);
             CONTENT_HOMENEEDS_URI= Uri.withAppendedPath(ItemEntry.CONTENT_HOMENEEDS_URI,path);
            CONTENT_HOUSEHOLD_URI = Uri.withAppendedPath(ItemEntry.CONTENT_HOMEHOLD_URI,path);
            CONTENT_PERSONALCARE_URI=Uri.withAppendedPath(ItemEntry.CONTENT_PERSONALCARE_LIST,path);
            CONTENT_TEACOFFEE_URI=Uri.withAppendedPath(ItemEntry.CONTENT_TEA_COFFEE_URI,path);


            Log.v("ItemProvider", CONTENT_BABYCARE_URI + "");
            Log.v("ItemProvider", CONTENT_URI + "");
            Log.v("ItemProvider", CONTENT_BREAKFAST_URI + "");
            Log.v("Item Provider uri", uri + "");
        } catch (Exception E) {
            Log.e("ItemProvider", "" + E);
        } finally {


            if (uri.equals(ItemEntry.CONTENT_URI) || uri.equals(CONTENT_URI)) {
                tableName = ItemEntry.TABLE_NAME;

            } else if (uri.equals(ItemEntry.CONTENT_CART_URI)) {
                tableName = ItemEntry.TABLE_NAME_CART;

            }
            else if (uri.equals(ItemEntry.CONTENT_ORDERS_URI) || uri.equals(CONTENT_ORDERS_URI)) {
                tableName = ItemEntry.TABLE_NAME_ORDERS;

            }
            else if (uri.equals(ItemEntry.CONTENT_WISHLIST_URI)) {
                tableName = ItemEntry.TABLE_NAME_WISHLIST;
            } else if (uri.equals(ItemEntry.CONTENT_BABYCARE_URI) || uri.equals(CONTENT_BABYCARE_URI)) {
                tableName = ItemEntry.TABLE_NAME_BABY_CARE;
            } else if (uri.equals(ItemEntry.CONTENT_BREAKFAST_URI) || uri.equals(CONTENT_BREAKFAST_URI)) {
                tableName = ItemEntry.TABLE_NAME_BREAKFAST;
            } else if (uri.equals(ItemEntry.CONTENT_DIRECTFROMFARMS_URI) || uri.equals(CONTENT_DIRECTFROMFARMS_URI)) {
                tableName = ItemEntry.TABLE_NAME_DIRECT_FROM_FARMS;
            } else if (uri.equals(ItemEntry.CONTENT_DRINKS_URI) || uri.equals(CONTENT_DRINKS_URI)) {
                tableName = ItemEntry.TABLE_NAME_DRINKS;
            } else if (uri.equals(ItemEntry.CONTENT_DRY_FRUITS_URI) || uri.equals(CONTENT_DRY_FRUITS_URI)) {
                tableName = ItemEntry.TABLE_NAME_DRY_FRUITS;
            } else if (uri.equals(ItemEntry.CONTENT_EDIBLE_OILS_URI) || uri.equals(CONTENT_EDIBLE_OILS_URI)) {
                tableName = ItemEntry.TABLE_NAME_EDIBLE_OILS;
            } else if (uri.equals(ItemEntry.CONTENT_FRAGRANCE_URI) || uri.equals(CONTENT_FRAGRANCE_URI)) {
                tableName = ItemEntry.TABLE_NAME_FRAGRANCE_LIST;
            } else if (uri.equals(ItemEntry.CONTENT_HEALTH_URI) || uri.equals(CONTENT_HEALTH_CARE_URI)) {
                tableName = ItemEntry.TABLE_NAME_HEALTH_CARE;
            } else if (uri.equals(ItemEntry.CONTENT_HEALTH_DRINKS_URI) || uri.equals(CONTENT_HEALTH_DRINKS_URI)) {
                tableName = ItemEntry.TABLE_NAME_HEALTH_DRINKS;
            }
            else  if(uri.equals(ItemEntry.CONTENT_USER_URI))
            {
                tableName = ItemEntry.TABLE_NAME_USERSLIST;
            }
            else if(uri.equals(ItemEntry.CONTENT_HOMENEEDS_URI) || uri.equals(CONTENT_HOMENEEDS_URI))
            {
                tableName = ItemEntry.TABLE_NAME_HOME_NEEDS;
            }
            else if(uri.equals(ItemEntry.CONTENT_HOMEHOLD_URI) || uri.equals(CONTENT_HOUSEHOLD_URI))
            {
                tableName = ItemEntry.TABLE_NAME_HOUSE_HOLD;
            }
            else if(uri.equals(ItemEntry.CONTENT_PERSONALCARE_LIST) || uri.equals(CONTENT_PERSONALCARE_URI))
            {
                tableName = ItemEntry.TABLE_NAME_PERSONAL_CARE;
            }
            else if(uri.equals(ItemEntry.CONTENT_TEA_COFFEE_URI) || uri.equals(CONTENT_TEACOFFEE_URI))
            {
                tableName = ItemEntry.TABLE_NAME_TEA_COFFEE;
            }



            Log.v("Item Provider", tableName);


            int match = sUriMatcher.match(uri);

            switch (match) {
                case ITEMS:
                    cursor = database.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                case ITEMS_ID:
                    selection = ItemEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                    cursor = database.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
                    break;
                default:
                    throw new IllegalArgumentException("Cannot parse query uri " + uri);
            }
            cursor.setNotificationUri(getContext().getContentResolver(), uri);


            return cursor;
        }
    }


    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return ItemEntry.CONTENT_LIST_TYPE;
            case ITEMS_ID:
                return ItemEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        Log.v("Item Provider", uri.toString());
        switch (match) {
            case ITEMS:
                if (uri.equals(CONTENT_CART_URI))
                    return insertItem(uri, contentValues, ItemEntry.TABLE_NAME_CART);
                else if (uri.equals(ItemEntry.CONTENT_WISHLIST_URI))
                    return insertItem(uri, contentValues, ItemEntry.TABLE_NAME_WISHLIST);
                else if (uri.equals(ItemEntry.CONTENT_ORDERS_URI))
                    return insertItem(uri, contentValues, ItemEntry.TABLE_NAME_ORDERS);
                else if(uri.equals(ItemEntry.CONTENT_USER_URI))
                    return  insertUser(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /**
     * Insert a item into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertItem(Uri uri, ContentValues values, String tableName) {

        // TODO: Insert a new item into the pets database table with the given ContentValues
        try {


            String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
            if (name == null || name.equals("")) {
                throw new IllegalArgumentException("Item requires a name");

            }
            int quantity = values.getAsInteger(ItemEntry.COLUMN_ITEM_QUANTITY);
            if (quantity == 0) {

                throw new IllegalArgumentException("Quantity is not valid");
            }
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), " Invalid quantity", Toast.LENGTH_SHORT).show();
            return null;
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        long newRowId = db.insert(tableName, null, values);

        if (newRowId == -1) {

            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        //notify all listeners that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);


    }

    public Uri insertUser(Uri uri, ContentValues values)
    {


                String name = values.getAsString(ItemEntry.COLUMN_ITEM_NAME);
                if (name == null || name.equals("")) {
                    throw new IllegalArgumentException("Item requires a name");

                }
                String address = values.getAsString(ItemEntry.COLUMN_ITEM_ADDRESS);
        if (address == null || address.equals("")) {
            throw new IllegalArgumentException("Item requires a address");

        }



    SQLiteDatabase db = mDbHelper.getReadableDatabase();


    long newRowId = db.insert(ItemEntry.TABLE_NAME_USERSLIST, null, values);

        if (newRowId == -1) {

        Log.e(LOG_TAG, "Failed to insert row for " + uri);
        return null;
    }

    //notify all listeners that the data has changed
    getContext().getContentResolver().notifyChange(uri, null);

    // Once we know the ID of the new row in the table,
    // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }


    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:

                return updateItem(uri, contentValues, selection, selectionArgs);
            case ITEMS_ID:
                // For the PET_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ItemEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(parseId(uri))};

                return updateItem(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int noOfRowsUpdated = db.update(ItemEntry.TABLE_NAME_CART, contentValues, selection, selectionArgs);
        boolean isPresent = contentValues.containsKey(ItemEntry.COLUMN_ITEM_NAME) || contentValues.containsKey(ItemEntry.COLUMN_ITEM_QUANTITY) ||
                contentValues.containsKey(ItemEntry.COLUMN_ITEM_PRICE) || contentValues.containsKey(ItemEntry.COLUMN_ITEM_IMAGE);

        if (noOfRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return noOfRowsUpdated;
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        int noofRowsDeleted = 0;
        Uri CONTENT_CART_URI=null;
        Uri CONTENT_WISHlIST_URI=null;
        Uri CONTENT_USER_URI = null;
        Uri CONTENT_ORDERS_URI=null;
        String tableName = " ";

        try {


            long id = ContentUris.parseId(uri);
            String path = "" + id;
             CONTENT_CART_URI= Uri.withAppendedPath(ItemEntry.CONTENT_CART_URI, path);
           CONTENT_WISHlIST_URI = Uri.withAppendedPath(ItemEntry.CONTENT_WISHLIST_URI, path);
            CONTENT_USER_URI = Uri.withAppendedPath(ItemEntry.CONTENT_USER_URI,path);
            CONTENT_ORDERS_URI = Uri.withAppendedPath(ItemEntry.CONTENT_ORDERS_URI,path);

        } catch (Exception E) {
            Log.e("ItemProvider", "" + E);
        } finally {


            if (uri.equals(ItemEntry.CONTENT_CART_URI) || uri.equals(CONTENT_CART_URI)) {
                tableName = ItemEntry.TABLE_NAME_CART;
            }
            else if(uri.equals(ItemEntry.CONTENT_USER_URI) || uri.equals( CONTENT_USER_URI))
            {
                tableName = ItemEntry.TABLE_NAME_USERSLIST;
            }
            else if(uri.equals(ItemEntry.CONTENT_ORDERS_URI) || uri.equals(CONTENT_ORDERS_URI))
            {
                tableName = ItemEntry.TABLE_NAME_ORDERS;
            }
            else {
                tableName = ItemEntry.TABLE_NAME_WISHLIST;
            }
            final int match = sUriMatcher.match(uri);
            switch (match) {
                case ITEMS:

                    // Delete all rows that match the selection and selection args
                    noofRowsDeleted = database.delete(tableName, selection, selectionArgs);
                    break;
                case ITEMS_ID:

                    // Delete a single row given by the ID in the URI
                    selection = ItemEntry._ID + "=?";
                    selectionArgs = new String[]{String.valueOf(parseId(uri))};

                    noofRowsDeleted = database.delete(tableName, selection, selectionArgs);
                    break;
                default: {
                    throw new IllegalArgumentException("Deletion is not supported for " + uri);
                }

            }
            if (noofRowsDeleted != 0) {
                getContext().getContentResolver().notifyChange(uri, null);

            }
            return noofRowsDeleted;
        }
    }
}
