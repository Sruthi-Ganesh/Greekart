package com.example.android.greekart1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SruthiGanesh on 8/1/2017.
 */

public class Search implements Parcelable {

    private String name;
    private String image;
    private int quantity;
    private Double price;
    private String uri;
    private String columnName=null;




    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(image);
        out.writeInt(quantity);
        out.writeDouble(price);
        out.writeString(uri);
        out.writeString(columnName);


    }
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Search> CREATOR
            = new Parcelable.Creator<Search>() {
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

   public Search(Parcel in) {
        name = in.readString();
        image=in.readString();
        quantity=in.readInt();
        price=in.readDouble();
        uri=in.readString();
        columnName = in.readString();
    }



    public Search(String name, String image,String uri, int quantity, Double price,String ColumName)
    {
        this.name= name;
        this.image= image;
        this.quantity =quantity;
        this.uri = uri;
        this.price = price;
        this.columnName = ColumName;

    }


    public String getName()
    {
        return name;
    }
    public int getQuantity()
    {
        return quantity;
    }
   public String getUri(){return uri;}
    public Double getPrice(){return price;}
    public String getImage(){return image;}
    public String getColumnName(){return columnName;}

}
