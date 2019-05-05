package com.example.android.greekart1;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class User {

    public String Name;
    public String Email;
    public String Address;
    public Double TotalPrice;
    public String Date;
    public String _id;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String _id, String name, String email, String address,Double total) {
        this.Name = name;
        this.Email = email;
        this._id = _id;
        this.Address = address;
        TotalPrice = total;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Date = dateFormat.format(date);



    }




    public class DataDB {

        public String email;
        public int quantity;
        public String product;
        public String _id;
        public Double price;
        private DatabaseReference mFirebaseDatabase;
        private FirebaseDatabase mFirebaseInstance;
        DataDB(String _id , String email, int quantity , String product,Double price)
        {
            this._id = _id;
            this.email=email;
            this.quantity = quantity;
            this.product = product;
            this.price = price;
        }

        DatabaseReference addToDatabase()
        {
            mFirebaseInstance = FirebaseDatabase.getInstance();

            // get reference to 'users' node
            mFirebaseDatabase = mFirebaseInstance.getReference("Data");
            mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

            return mFirebaseDatabase;

        }
    }

}
