package com.example.android.greekart1;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by SruthiGanesh on 7/30/2017.
 */

public class DataDB {

    public int quantity;
    public String product;
    public String _id;
    public Double price;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    DataDB(String _id , int quantity , String product,Double price)
    {
        this._id = _id;
        this.quantity = quantity;
        this.product = product;
        this.price = price;
    }


}
