package com.example.android.greekart1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by SruthiGanesh on 8/6/2017.
 */

public class CancellationActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancellation_activity);

        String name = getIntent().getStringExtra("Product");
        int quantity = getIntent().getIntExtra("Quantity",0);
        int resourceid = getIntent().getIntExtra("Image",0);
        Double price = getIntent().getDoubleExtra("Price",0.0);


        TextView nametxt = (TextView) findViewById(R.id.name);
        TextView pricetxt = (TextView) findViewById(R.id.summary);
        TextView quantitytxt = (TextView) findViewById(R.id.quantityTextView);
        ImageView img = (ImageView) findViewById(R.id.image_item);

        nametxt.setText(name);
        pricetxt.setText(price.toString());
        img.setImageResource(resourceid);
        quantitytxt.setText(String.valueOf(quantity));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Cancelled Order");
        toolbar.inflateMenu(R.menu.menu_bar);
        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.action_cart:
                        showCart();
                        return true;
                    case R.id.search:
                        search();
                        return true;


                }
                return CancellationActivity.super.onOptionsItemSelected(item);
            }






        });





    }


    public void showCart()
    {
        CartActivity cart = new CartActivity(this);
    }


    public void search()
    {
        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }
    }



