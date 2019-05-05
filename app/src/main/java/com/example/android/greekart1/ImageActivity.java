package com.example.android.greekart1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 6/5/2017.
 */

public class ImageActivity extends Activity {
    String imageRef;
    int iden=0;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }
    public ImageActivity()
    {
    }
    public ImageActivity(String image,Context context) {
        try {
            imageRef=image;
            Log.v("Image Activity",imageRef);
            String PACKAGE_NAME = context.getPackageName();
            Log.v("Image Activity",PACKAGE_NAME);
            iden = context.getResources().getIdentifier(imageRef, "drawable", PACKAGE_NAME);
            Log.v("Image Activity","" + iden);
        } catch (NullPointerException E) {
            Log.e("Image Activity", "Exception at getResources");
        }
    }


    public int getImageId() {

        return iden;
    }

}
