package com.example.android.greekart1.data;

import android.content.ContentUris;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemContract.ItemEntry;


import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


/**
 * Created by SruthiGanesh on 6/4/2017.
 */

public class ItemHelper extends SQLiteAssetHelper {
    public static final String LOG_TAG = ItemHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "groceries.db";




    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    public ItemHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    /**
     * This is called when the database is created for the first time.
     */

    public void onCreateDatabase(SQLiteDatabase db,String tableName) {
        // Create a String that contains the SQL statement to create the pets table

        String SQL_CREATE_ITEMS_TABLE =  "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_PRICE + " REAL NOT NULL, "
                + ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + ItemEntry.COLUMN_ITEM_IMAGE + " TEXT NOT NULL,"
                + ItemEntry.URI + " TEXT NOT NULL);";

        Log.v(LOG_TAG,  SQL_CREATE_ITEMS_TABLE);
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);





    }

    public void onCreate(SQLiteDatabase db,String tableName) {
        // Create a String that contains the SQL statement to create the pets table

        String SQL_CREATE_ITEMS_TABLE =  "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_ADDRESS + " TEXT NOT NULL);";

        Log.v(LOG_TAG,  SQL_CREATE_ITEMS_TABLE);
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);





    }

    public void onCreateDatabaseOrder(SQLiteDatabase db,String tableName) {
        // Create a String that contains the SQL statement to create the pets table

        String SQL_CREATE_ITEMS_TABLE =  "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_PRICE + " REAL NOT NULL, "
                + ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
    + ItemEntry.USERID + " TEXT NOT NULL, "
            + ItemEntry.PRODUCT_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_IMAGE + " TEXT NOT NULL,"
                + ItemEntry.URI + " TEXT NOT NULL);";

        Log.v(LOG_TAG,  SQL_CREATE_ITEMS_TABLE);
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);





    }



}
