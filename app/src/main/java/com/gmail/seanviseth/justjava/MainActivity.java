package com.gmail.seanviseth.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    int pricePerCup = 5;
    int whippedCreamPrice = 1;
    int chocolatePrice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        TextView textView = new TextView(this);
//        textView.setText("Wow!");
//        setContentView(textView);
    }

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
        //displayPrice(quantity * pricePerCup);
    }

    public void decrement(View view) {
        if (quantity > 0)
            quantity = quantity - 1;
        displayQuantity(quantity);
        //displayPrice(quantity * pricePerCup);
    }

    /**
     * Email Intent
     */
    public void composeEmail(String[] toEmailAddress, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, toEmailAddress);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(quantity, hasWhippedCream, hasChocolate);
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();
        String priceMessage = createOrderSummary(quantity, price, hasWhippedCream, hasChocolate, name);
        String subject = "Just Java order for " + name;
        String[] toEmailAddress = {"justjavademo@gmail.com"};
        composeEmail(toEmailAddress, subject, priceMessage);
    }

    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        //int price = quantity * pricePerCup;
        int unitPrice = pricePerCup;
        if (hasWhippedCream)
            unitPrice += whippedCreamPrice;
        if (hasChocolate)
            unitPrice += chocolatePrice;
        return quantity * unitPrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private String createOrderSummary (int quantity, int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String summary = "Name: " + name;
        summary += "\nAdd whipped cream? " + hasWhippedCream;
        summary += "\nAdd Chocolate? " + hasChocolate;
        summary += "\nQuantity: " + quantity;
        summary += "\nTotal: $" + price;
        summary += "\nThank you!";
        return summary;
    }


}