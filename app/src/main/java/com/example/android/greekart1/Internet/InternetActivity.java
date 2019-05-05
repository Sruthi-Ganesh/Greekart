package com.example.android.greekart1.Internet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.greekart1.R;

public class InternetActivity extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener {

   private Context context;



        // Manually checking internet connection
    public InternetActivity(Context context)
    {
        this.context = context;




    }

    public boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected(context);
        return isConnected;
    }



    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();
    }
}
