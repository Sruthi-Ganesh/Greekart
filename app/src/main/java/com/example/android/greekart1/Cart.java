package com.example.android.greekart1;

import android.net.Uri;

/**
 * Created by SruthiGanesh on 8/12/2017.
 */

public class Cart {
    public String name;
    public Double price;
    public String image;
    public  int quantity;
    public  String uri;

    public Cart(String name, Double price , String image , int quantity , String uri)
    {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.uri = uri;
    }

    public Cart(String name, Double price , String image , String uri)
    {
        this.name = name;
        this.price = price;
        this.image = image;
        this.uri = uri;
    }
}
