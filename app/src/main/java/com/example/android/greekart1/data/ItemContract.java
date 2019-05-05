package com.example.android.greekart1.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SruthiGanesh on 6/4/2017.
 */

public class ItemContract {
    //Provider authority , package name
    public static final String CONTENT_AUTHORITY = "com.example.android.greekart1";

    //adding schema to the content authority
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //table name of the DB
    public static final String PATH_ITEMS = "groceries";
    public static final String PATH_BABY="babycare";
    public static final String PATH_BREAKFAST ="breakfast";
    public static final String PATH_DIRECT_FARMS = "directfromfarms";
    public static final String PATH_CART = "cart";
    public static final String PATH_WISH="wish";
    public static final String PATH_DRINKS="drinks";
    public static final String PATH_DRY_FRUITS="dryfruits";
    public static final String PATH_EDIBLE_OILS="edibleoils";
    public static final String PATH_FRAGRANCE="fragrance";
    public static final String PATH_HEALTH_CARE="healthcare";
    public static final String PATH_HEALTH_DRINKS="healthdrinks";
    public static final String PATH_HOME_NEEDS="homeneedslist";
    public static final String PATH_ORDERS_LIST="orderslist";
    public static final String PATH_USER_LIST="userslist";
    public static final String PATH_HOUSEHOLD_LIST="householdlist";
    public static final String PATH_PERSONALCARE_LIST="personalcare";
    public static final String PATH_TEACOFFEE_LIST="teacoffee";



    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private ItemContract() {}

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class ItemEntry implements BaseColumns {

        /** Name of database table for pets */
        public final static String TABLE_NAME = "grocerieslist";
        public final static String TABLE_NAME_CART = "cartlist";
        public final static String TABLE_NAME_WISHLIST="wishlist";
        public final static String TABLE_NAME_BABY_CARE="babycarelist";
        public final static String TABLE_NAME_BREAKFAST="breakfastlist";
        public final static String TABLE_NAME_DIRECT_FROM_FARMS="directfromfarmslist";
        public final static String TABLE_NAME_DRINKS = "drinkslist";
        public final static String TABLE_NAME_DRY_FRUITS="dryfruitslist";
        public final static String TABLE_NAME_EDIBLE_OILS="edibleoilslist";
        public final static String TABLE_NAME_FRAGRANCE_LIST="fragrancelist";
        public final static String TABLE_NAME_ORDERS="orderslist";
        public final static String TABLE_NAME_HEALTH_CARE="healthcarelist";
        public final static String TABLE_NAME_HEALTH_DRINKS="healthdrinkslist";
        public final static String TABLE_NAME_USERSLIST="userslist";
        public final static String TABLE_NAME_HOME_NEEDS="homeneedslist";
        public final static String TABLE_NAME_HOUSE_HOLD="householdlist";
        public final static String TABLE_NAME_PERSONAL_CARE="personalcarelist";
        public final static String TABLE_NAME_TEA_COFFEE="teacoffeelist";




        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_ITEM_NAME ="name";

        public final static String COLUMN_ITEM_PRICE = "price";




        public final static String COLUMN_ITEM_QUANTITY = "quantity";


        public final static String COLUMN_ITEM_IMAGE = "imagesource";
        public final static String COLUMN_ITEM_ADDRESS = "address";
        public final static String URI = "uri";
        public final static String USERID = "userid";
        public final static String PRODUCT_NAME = "productname";








        //appending table name with the base content uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);
        public static final Uri CONTENT_CART_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CART);
        public static final Uri CONTENT_BREAKFAST_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BREAKFAST);
        public static final Uri CONTENT_BABYCARE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BABY);
        public static final Uri CONTENT_DRINKS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DRINKS);
        public static final Uri CONTENT_DRY_FRUITS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DRY_FRUITS);
        public static final Uri CONTENT_EDIBLE_OILS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EDIBLE_OILS);
        public static final Uri CONTENT_FRAGRANCE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FRAGRANCE);
        public static final Uri CONTENT_HEALTH_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HEALTH_CARE);
        public static final Uri CONTENT_HEALTH_DRINKS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HEALTH_DRINKS);
        public static final Uri CONTENT_DIRECTFROMFARMS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DIRECT_FARMS);
        public static final Uri CONTENT_ORDERS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ORDERS_LIST);
        public static final Uri CONTENT_USER_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USER_LIST);
        public static final Uri CONTENT_HOMENEEDS_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HOME_NEEDS);
        public static final Uri CONTENT_HOMEHOLD_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_HOUSEHOLD_LIST);
        public static final Uri CONTENT_PERSONALCARE_LIST = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PERSONALCARE_LIST);
        public static final Uri CONTENT_TEA_COFFEE_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TEACOFFEE_LIST);



        public static final Uri CONTENT_WISHLIST_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_WISH);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;




    }

}
