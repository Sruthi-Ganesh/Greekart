package com.example.android.greekart1;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.android.greekart1.data.ItemContract;
import com.example.android.greekart1.data.ItemContract.ItemEntry;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.greekart1.data.ItemHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.greekart1.R.drawable.i;

/**
 * Created by SruthiGanesh on 6/5/2017.
 */

public class ProductDescription extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    Uri currentUri=null;
    ImageView imageView;
    private int addToCart=0;
    TextView textName,outOfStockText;
    ItemHelper mDbHelper;
    String key=null;
    SQLiteDatabase database;
    TextView  textPrice,textQuantity;
    final int[] ispresent = {0} ;
    private int ITEM_LOADER=0;
    final int[] ispresentWishList = {0};
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private long id;
    private int quantity,getQuantity;
    private int noOfItemQuantities;
    private String userId;
    private String names;
    SharedPreference sharedPreference;

    int valueAdded=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_description);
        Intent i = getIntent();
        getQuantity = i.getIntExtra("Quantity",0);
        currentUri = i.getData();
        Log.v("Product Description",currentUri + "");
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("user_info");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

         imageView = (ImageView) findViewById(R.id.image_product_desc);
         textName = (TextView) findViewById(R.id.text_product_desc_name);
          textPrice = (TextView) findViewById(R.id.text_product_desc_price);
          textQuantity = (TextView) findViewById(R.id.editTextQuantity);
        outOfStockText = (TextView) findViewById(R.id.outOfStock);
        getLoaderManager().initLoader(ITEM_LOADER,null,this);
         id = ContentUris.parseId(currentUri);
        Log.v("Product Description",String.valueOf(id));
        mDbHelper = new ItemHelper(getApplicationContext());



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity);
        toolbar.setTitle("Product Description");
        toolbar.inflateMenu(R.menu.menu_bar);
        toolbar.setNavigationIcon(R.drawable.icons_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
       sharedPreference = new SharedPreference(getApplicationContext());
        sharedPreference.sharedPreferenceNumber();


            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            userId = currentFirebaseUser.getUid();



        Log.v("UserId",userId);


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
                return ProductDescription.super.onOptionsItemSelected(item);
            }






        });


        CartChecking check = new CartChecking();
        check.execute();
        WishLisChecking wish = new WishLisChecking();
        wish.execute();



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


     public void addToCart(String name, Double price , String image , int quantity)
    {
     /*SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        sharedPreference.putSharedPreference(id);
        Set<String> IdSet = sharedPreference.getSharedPreference();
        Log.v("Product Description",IdSet.toString());*/
     if(addToCart==1)
     {
         return;
     }
        addToCart = 1;
 noOfItemQuantities = Integer.parseInt(textQuantity.getText().toString());

        if(noOfItemQuantities>quantity ) {
            addToCart=2;
            outOfStockText.setText("Out of Stock");
            return;
        }
        double totalPrice = price * noOfItemQuantities;






        final Cart cart= new Cart(name,totalPrice,image,noOfItemQuantities,currentUri.toString());


        addToFirebase(cart,ItemEntry.TABLE_NAME_CART);
        dataSnapShot(ItemEntry.TABLE_NAME_CART);

        ispresent[0]=0;







    }
    public void addToFirebase(Cart cart , final String tableName)
    {
        if(tableName.equals(ItemEntry.TABLE_NAME_CART))
        {
            valueAdded = sharedPreference.getSharedPreferenceI() +1;
        }
        else
        {
            valueAdded = sharedPreference.getSharedPreferenceWish() +1;
        }


        Log.v("numbers", " " + valueAdded);
        mFirebaseDatabase.child(userId).child(tableName).child(String.valueOf(valueAdded)).setValue(cart).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    Toast.makeText(getApplicationContext(), "Added to " +tableName, Toast.LENGTH_LONG).show();
            }
        } );



    }
    public void dataSnapShot(final String tableName)
    {



        Query lastQuery = mFirebaseDatabase.child(userId).child(tableName).orderByKey().limitToLast(1);
        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objSnapshot: dataSnapshot.getChildren()) {
                    if(tableName.equals(ItemEntry.TABLE_NAME_CART)) {
                        sharedPreference.putSharedPreferenceI(Integer.parseInt(objSnapshot.getKey()));
                    }
                    else
                    {
                        sharedPreference.putSharedPreferenceWish(Integer.parseInt(objSnapshot.getKey()));
                    }
                    Log.v("Product",objSnapshot.getKey());
                }

            }


        @Override
        public void onCancelled(DatabaseError databaseError) {
        //Handle possible errors.
    }});



    }





    public int isNotPresent()  {

                return ispresent[0];





    }


public int isNotPresentWishList()
{
    return  ispresentWishList[0];
}


    public void addToDatabase(String name, Double price, String image) {






        final Cart cart= new Cart(name,price,image,currentUri.toString());


        addToFirebase(cart,ItemEntry.TABLE_NAME_WISHLIST);
        dataSnapShot(ItemEntry.TABLE_NAME_WISHLIST);

        ispresentWishList[0] = 0;
        }



@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        String projection[] = { ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_IMAGE,
                ItemEntry.COLUMN_ITEM_QUANTITY};





    return new CursorLoader(this,currentUri,projection,null,null,null);




    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        final String name,image;
        final Double price;


        if(currentUri!=null) {
            if (cursor.moveToFirst()) {
                int nameColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_NAME);
                int priceColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_PRICE);
                int imageColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_IMAGE);
                int quantityColumnIndex = cursor.getColumnIndex(ItemEntry.COLUMN_ITEM_QUANTITY);

                if(getQuantity!=0)
                {
                    TextView ediText = (TextView) findViewById(R.id.editTextQuantity);
                    ediText.setText(String.valueOf(getQuantity));
                }


                // Extract out the value from the Cursor for the given column index
                name = cursor.getString(nameColumnIndex);
                names = name;
                 price = cursor.getDouble(priceColumnIndex);
                 image = cursor.getString(imageColumnIndex);
                 quantity = cursor.getInt(quantityColumnIndex);

                int resourceId = new ImageActivity(image,getApplicationContext()).getImageId();
                textName.setText(name);
                textPrice.setText("Rs. " + String.valueOf(price));
                imageView.setImageResource(resourceId);
                 final int addTocartValue=0;




                final Button addToCartButton = (Button) findViewById(R.id.addtoccart);
                addToCartButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {





                        Log.v("bool sync main",ispresent[0] + " ");
                        final int  value =isNotPresent();
                        Log.v("sync main" ," " + value + ispresent[0]);


                        if(value==2) {

                            addToCart(name, price, image, quantity);
                        }
                        else if(value==0)
                        {
                            Toast.makeText(getApplicationContext(),"Already in cart",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            updateCart();
                        }






                    }
                });
                final Button addToCartWish = (Button) findViewById(R.id.addtowishlist);
                addToCartWish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int  value =isNotPresentWishList();

                        if(value==2) {

                            addToDatabase(name,price,image);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"Item Already in WishList",Toast.LENGTH_SHORT).show();
                        }



                    }
                });

                Button BuyNowButton = (Button) findViewById(R.id.buyNow);
                BuyNowButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int value =isNotPresent();
                        if (addTocartValue == 0 && value==2) {
                            addToCart(name, price, image, quantity);
                        }
                        else if (addToCart != 2 && value==0) {
                            CartActivity cart = new CartActivity(ProductDescription.this);
                        }
                        else
                        {
                            updateCart();
                        }
                    }
                });

                }


            }


        }

        public void updateCart()
        {

            mFirebaseDatabase.child(userId).child(ItemEntry.TABLE_NAME_CART).child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    noOfItemQuantities = Integer.parseInt(textQuantity.getText().toString());
                    int quantity =  Integer.parseInt(dataSnapshot.child("quantity").getValue().toString().trim());
                    Double price = Double.parseDouble(dataSnapshot.child("price").getValue().toString().trim());
                    final Double totalPrice = (price/quantity) * noOfItemQuantities;



                    mFirebaseDatabase.child(userId).child(ItemEntry.TABLE_NAME_CART).child(key).child("quantity").setValue(noOfItemQuantities).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Cart updated to quantity: " + noOfItemQuantities, Toast.LENGTH_LONG).show();
                        }
                    });
                    mFirebaseDatabase.child(userId).child(ItemEntry.TABLE_NAME_CART).child(key).child("price").setValue(totalPrice).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Cart updated to price: " + totalPrice, Toast.LENGTH_LONG).show();
                        }
                    });

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            ispresent[0]=0;
        }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        textName.setText("");
        textPrice.setText("");
        imageView.setImageResource(0);


    }

    public void increment(View v)
    {
        int oldQuantity = Integer.parseInt(textQuantity.getText().toString());
        int newQuantity = oldQuantity+1;
        if(newQuantity>quantity) {
            Toast.makeText(this, "Out of Stock", Toast.LENGTH_SHORT).show();
            TextView outOfStockText = (TextView) findViewById(R.id.outOfStock);
            outOfStockText.setText("Out Of Stock");
            return;
        }
        textQuantity.setText(String.valueOf(newQuantity));
        Log.v("sync increment" ," " + ispresent[0]);
        CartChecking checking = new CartChecking();
        checking.execute();

    }
    public void decrement(View v)
    {
        int oldQuantity = Integer.parseInt(textQuantity.getText().toString());
        int newQuantity = oldQuantity-1;
        if(newQuantity<1)
        {
            Toast.makeText(this,"Quantity cannot be lesser than 1",Toast.LENGTH_SHORT).show();
            return;
        }
        textQuantity.setText(String.valueOf(newQuantity));

        CartChecking checking = new CartChecking();
        checking.execute();
        Log.v("sync decrement" ," "  + ispresent[0]);

    }
    public class CartChecking extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_info").child(userId).child(ItemContract.ItemEntry.TABLE_NAME_CART);
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {



                                //Get map of users in datasnapshot
                                noOfItemQuantities = Integer.parseInt(textQuantity.getText().toString());

                                //iterate through each user, ignoring their UID
                                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                                    String name = messageSnapshot.child("name").getValue().toString();



                                    int quantity = Integer.parseInt(messageSnapshot.child("quantity").getValue().toString());
                                    if (name.equals(names)) {
                                        if (noOfItemQuantities == quantity) {

                                            ispresent[0] = 0;
                                            Log.v("Sync back ", ispresent[0] + names + noOfItemQuantities);
                                            return;

                                        } else {
                                            key = messageSnapshot.getKey();
                                            ispresent[0] = 1;
                                            Log.v("Sync back", ispresent[0] + names + noOfItemQuantities);
                                            return;


                                        }


                                    }
                                }


                                ispresent[0] = 2;

                                Log.v("Sync back inout", ispresent[0] + names + noOfItemQuantities);


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });
            Log.v("Sync back outside", ispresent[0] + names + noOfItemQuantities);
            if(names==null) {
                ispresent[0] =2;

            }

            return ispresent[0];

        }
        @Override
        protected void onPostExecute(Integer result) {
            if(result==2) {
                ispresent[0] = 2;
            }
            Log.v("Sync onpost" , result + names + noOfItemQuantities);
        }
    }


    public class WishLisChecking extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            // TODO Auto-generated method stub
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user_info").child(userId).child(ItemEntry.TABLE_NAME_WISHLIST);
            ref.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //Get map of users in datasnapshot
                           if(dataSnapshot!=null) {

                               //iterate through each user, ignoring their UID
                               for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                                   String name = messageSnapshot.child("name").getValue().toString();


                                   if (name.equals(names)) {

                                       ispresentWishList[0] = 0;
                                       return;

                                   }


                               }
                           }

                                ispresentWishList[0] = 2;

                                Log.v("Sync back", ispresent[0] + names + noOfItemQuantities);

                            }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            //handle databaseError
                        }
                    });

            return ispresentWishList[0];

        }
        @Override
        protected void onPostExecute(Integer result) {
            if(result==2) {
                ispresentWishList[0] = 2;
            }
            Log.v("Sync onpost" , result + names );
        }
    }

}


