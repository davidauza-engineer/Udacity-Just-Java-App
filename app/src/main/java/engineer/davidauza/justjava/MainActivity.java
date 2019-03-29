package engineer.davidauza.justjava;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int mQuantity = 1;
    private String mTotal;
    private TextView mQuantityTextView;
    private TextView mOrderSummaryTextView;
    private CheckBox whippedCreamCheckBox;
    private CheckBox chocolateCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the Views needed
        mQuantityTextView = findViewById(R.id.quantity_text_view);
        mOrderSummaryTextView = findViewById(R.id.order_summary_text_view);
        // Find Whipped cream CheckBox to be able to verify if it is checked
        whippedCreamCheckBox = findViewById(R.id.whipped_cream_check_box);
        // Find Chocolate CheckBox to be able to verify if it is checked
        chocolateCheckBox = findViewById(R.id.chocolate_check_box);

        updateTotal();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find Name EditText to be able to get the text from it
        EditText nameEditText = findViewById(R.id.name_edit_text);

        String user = nameEditText.getText().toString();

        String emailSubject = "JustJava order for " + user;

        String emailBody = "Name: " + user +
                "\nAdd whipped cream? " + whippedCreamCheckBox.isChecked() +
                "\nAdd chocolate? " + chocolateCheckBox.isChecked() +
                "\nQuantity: " + mQuantity +
                "\nTotal: " + mTotal +
                "\nThank you!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        // Only email apps should handle this
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailBody);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Show a warning toast to let the user know he needs to install an email app to send
            // the order
            createWarningToast(R.string.main_email_warning);
        }

        // Show the order summary
        mOrderSummaryTextView.setText(emailBody);
    }

    /**
     * This method adds one to the number of coffees and displays it on the screen if the value of
     * mQuantity is less than one hundred, else it shows a warning as orders cannot exceed one
     * hundred coffees. It is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (mQuantity < 100) {
            mQuantity++;
            updateTotal();
        } else {
            createWarningToast(R.string.main_order_warning_one_hundred);
        }
    }

    /**
     * This method subtracts one to the number of coffees and displays it on the screen if the
     * value of mQuantity is greater than one, else it shows a warning as orders cannot be negative.
     * It is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (mQuantity > 1) {
            mQuantity--;
            updateTotal();
        } else {
            createWarningToast(R.string.main_order_warning_one);
        }
    }

    /**
     * This method defines the total String and updates the UI accordingly
     */
    private void updateTotal() {
        final int PRICE_PER_CUP = 5;
        final int WHIPPED_CREAM_TOPPING = 1;
        final int CHOCOLATE_TOPPING = 2;

        int finalUnitPrice = PRICE_PER_CUP;

        if (whippedCreamCheckBox.isChecked()) {
            finalUnitPrice += WHIPPED_CREAM_TOPPING;
        }

        if (chocolateCheckBox.isChecked()) {
            finalUnitPrice += CHOCOLATE_TOPPING;
        }

        mTotal = NumberFormat.getCurrencyInstance().format(finalUnitPrice * mQuantity);

        // Update UI
        mQuantityTextView.setText(Integer.toString(mQuantity));
        mOrderSummaryTextView.setText(mTotal);
    }

    /**
     * This method is called when a topping is added so the variables and UI get updated properly
     *
     * @param view
     */
    public void updateTotal(View view) {
        updateTotal();
    }

    /**
     * This method creates a warning toast if the user tries to decrease quantity below 1, or to
     * increase quantity above 100. It is also used if the user does not have an email app
     * installed when trying to send an order.
     *
     * @param pText The ID to locate the appropriate String at the strings.xml file
     */
    private void createWarningToast(int pText) {
        Toast toast = Toast.makeText(this, pText, Toast.LENGTH_SHORT);

        // If the user is using an API greater than or equal to 21, styles the toast
        if (Build.VERSION.SDK_INT >= 21) {
            View viewToast = toast.getView();
            viewToast.setBackgroundTintList(ColorStateList.valueOf(getResources().
                    getColor(R.color.colorAccent)));
            TextView text = viewToast.findViewById(android.R.id.message);
            text.setTextColor(getResources().getColor(R.color.white));
        }

        toast.show();
    }
}