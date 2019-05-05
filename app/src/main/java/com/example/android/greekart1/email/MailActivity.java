package com.example.android.greekart1.email;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.greekart1.R;

import java.util.ArrayList;

public class MailActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    Context context;
    private String text;


    public MailActivity(Context context) {

        this.context = context;

    }

    public void sendMail() {


        // TODO Auto-generated method stub

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mail sender = new mail("sruthibadal@gmail.com",
                            "20121996Srutz");

                    sender.sendMail("Hello from JavaMail", "Body from JavaMail",
                            "sruthibadal@gmail.com", "sruthibadal@gmail.com",text);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();


    }

    public void setTextConfirmation(final ArrayList<String> products,final ArrayList<Integer> quantities,final ArrayList<String> prices)
    {
         text = "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "\n" +
                "\n" +
                "#name{\n" +
                "    background-color: lightgrey; " +
                "  text-align: right;" +
                "\n" +
                "}\n" +
                "\n" +
                "\n" +
                "</style>\n" +
                "</head>\n" +
                "<body >\n" +
                "\n" +
                "\n" +
                "<font size='5'> <b> Your order Confirmation </b> </font> "+
                "<div id='name' style='position:absolute; top:25px; left:300px; '> <font size='4'>  To the address: <br> Sankara Ganapaths Flats </font> </div>\n" +
                "</div>\n" +
                "<br>\n" +
                "<b> Hello Sruthi,  </b>\n" +
                "<br>\n" +
                "<br>\n" +
                "    We thought you'd like to know that your items are dispatched. Your order is on the way. If you need to return an item from this shipment, please visit Your Orders on Greekart.\n" +
                "\t<br>\n" +
                "\t <br>\n" +
                "\t \n" ;
        for(int i=0;i<products.size();i++) {
            text += " <br> <br>  <div style='position:absolute;  left:300px;'><font size='4'> <b> Product: \t </b> "+ products.get(i) + " <br> \n  <b> Quantity: \t </b>" +
                    quantities.get(i)+ "</font> </div>\n" +
                    " <div style='position:absolute;  left:500px;'><font size='3'> <b> Price: \t </b>" + prices.get(i) + "</font> </div>\n" +
                    " \n";

        }
        text +="</body>\n" +
                "</html>";
    }


    public void setTextSignUp()
    {
       text = "<html>\n" +
                "<body>\n" +
                "<h1> Welcome! <h1>\n" +
                "<p > <font size='4'> Hello ,<br>  Thanks for registering in greekart. For purchasing Groceries, BreakFast food, Drinks and more, Please visit Greekart App <br> \n" +
                "For further queries contact sruthibadal@gmail.com </font> </p>\n" +
                "</body>\n" +
                "</html>";
    }
}