


package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     * if you click on the plus button with 100 showing, it will not increment any further.
     * it will increment if it seems 99, and that's it.
     */
    public void increment(View view) {
        quantity = quantity + 1;

        if ((quantity == 99) || (quantity > 100)) {
            Context context = getApplicationContext();
            CharSequence text = "you cannot have more than 100 coffees";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     * if you click the minus button while 1 is being shown, it will stick on one and
     * show toast message
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Context context = getApplicationContext();
            CharSequence text = "You cannot have less than 1 coffee";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        quantity = (quantity - 1);


        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     * pass price to the  createOrderSummary method.return value of the method gets stored in the priceMessage variable
     *
     * Log.v("MainActivity", "Has whipped cream: " +hasWhippedCream);  used to log after the boolean is saved
     */
    public void submitOrder(View view) {
        //find the user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        //figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);






               Intent intent = new Intent(Intent.ACTION_SENDTO);
               intent.setData(Uri.parse("mailto:")); // only email apps should handle this
              // intent.putExtra(Intent.EXTRA_EMAIL, "jcatan@optonline.net");
               intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
               intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
               if (intent.resolveActivity(getPackageManager()) != null) {
                   startActivity(intent);
               }


               displayMessage(priceMessage);
           }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }



    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *@param addWhippedCream pass whether cust wants whipped cream .adds $1 if cust wants it
     *  @param addChocolate pass whether cust wants choc .adds $2
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream){
        basePrice = basePrice + 1;
        }


        if (addChocolate){
        basePrice = basePrice+2;
        }

        //calculate the total order price by multiplying the number of cups by the base price
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     *@param name shows cust name in order summary
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     *
     * sets up the message
     * keeps appending the new portion of the message to the existing and updating the value
     * of priceMessage
     * can also be done with += both are used below as examples
     *
     *
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage +=  "\nTotal $" + price;
        priceMessage +=  "\nThank you!";
        return priceMessage;

    }
}