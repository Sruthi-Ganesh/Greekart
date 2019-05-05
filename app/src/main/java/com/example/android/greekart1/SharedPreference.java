package com.example.android.greekart1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 6/8/2017.
 */

public class SharedPreference {
    SharedPreferences sharedPreferencesEmail ;
    SharedPreferences sharedPreferenceId;
    SharedPreferences sharedPreferenceNum;
    SharedPreferences sharedPreferenceWishList;
    SharedPreferences sharedPreferenceOrder;
    String email = null;
    String id = null;
    int number=0;
    int numberWishList = 0;
    private int numberOrder = 0;
    private Context context;
    SharedPreferences.Editor editorEmail;
    SharedPreferences.Editor editorId;
    private SharedPreferences.Editor editorNum;
    SharedPreferences.Editor editorWishList;
    SharedPreferences.Editor  editorOrder;


    public SharedPreference(Context context)
    {
        this.context = context;
    }

    public void SharedPreferenceEmail()
    {
        sharedPreferencesEmail = context.getSharedPreferences("Email", context.MODE_PRIVATE);
         editorEmail = sharedPreferencesEmail.edit();





    }
   public void sharedPreferenceUserID()
    {
        sharedPreferenceId = context.getSharedPreferences("Id", context.MODE_PRIVATE);
        editorId = sharedPreferenceId.edit();




    }
    public void sharedPreferenceNumber()
    {
        sharedPreferenceNum = context.getSharedPreferences("Number", context.MODE_PRIVATE);
        editorNum = sharedPreferenceNum.edit();
        sharedPreferenceWishList = context.getSharedPreferences("WishList", context.MODE_PRIVATE);
        editorWishList = sharedPreferenceWishList.edit();
        sharedPreferenceOrder = context.getSharedPreferences("Order", context.MODE_PRIVATE);
        editorOrder = sharedPreferenceOrder.edit();



    }




    public void putSharedPreference(String email )
    {

        this.email = email;
        Log.v("Shared Preference",email);
        editorEmail.clear();
        editorEmail.putString("Id",email);
        editorEmail.apply();

    }

    public String getSharedPreference()
    {

        String id = sharedPreferencesEmail.getString("Id",null);
        if(id==null)
        {
            Log.e("Shared Preference","email is null");
                    return null;
        }
        return id;
    }

    public void putSharedPreferenceWish(int wishList )
    {

        this.numberWishList = wishList;
        Log.v("Shared Preference"," " + wishList);
        editorWishList.clear();
        editorWishList.putInt("NumberWish",wishList);
        editorWishList.apply();

    }

    public int getSharedPreferenceWish()
    {

        int id = sharedPreferenceWishList.getInt("NumberWish",0);
        if(id==0)
        {
            Log.e("Shared Preference","id is null");
            return 0;
        }
        return id;
    }
    public void putSharedPreferenceOrder(int wishList )
    {

        this.numberOrder = wishList;
        Log.v("Shared Preference"," " + wishList);
        editorOrder.clear();
        editorOrder.putInt("NumberOrder",wishList);
        editorOrder.apply();

    }

    public int getSharedPreferenceOrder()
    {

        int id = sharedPreferenceOrder.getInt("NumberOrder",0);
        if(id==0)
        {
            Log.e("Shared Preference","id is null");
            return 0;
        }
        return id;
    }

    public void clearSharedPreferenceNum()
    {
        editorNum.clear();
        editorNum.putInt("num",0);
        editorNum.apply();
        number= 0;

    }


    public void putSharedPreferenceI(int number )
    {


        this.number = number;
        Log.v("Shared Preference",number + "");
        editorNum.clear();
        editorNum.putInt("num",number);
        editorNum.apply();

    }

    public int getSharedPreferenceI()
    {
        int id = sharedPreferenceNum.getInt("num",0);
        if(id==0)
        {
            Log.e("Shared Preference","numb is null");
            return 0;
        }
        return id;
    }

}
