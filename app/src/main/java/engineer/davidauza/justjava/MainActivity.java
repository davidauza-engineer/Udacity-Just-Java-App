package engineer.davidauza.justjava;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private final int PRICE = 5;
    private int mQuantity = 1;
    private String mTotal;
    private TextView mQuantityTextView;
    private TextView mOrderSummaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuantityTextView = findViewById(R.id.quantity_text_view);
        mOrderSummaryTextView = findViewById(R.id.order_summary_text_view);
        updateTotal();
        updateScreen(mQuantity, mTotal);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage = createOrderSummary(mQuantity, mTotal);
        mOrderSummaryTextView.setText(priceMessage);
    }

    /**
     * This method updates the values of quantity and total in the screen
     */
    private void updateScreen(int quantity, String total) {
        mQuantityTextView.setText(Integer.toString(quantity));
        mOrderSummaryTextView.setText(total);
    }

    /**
     * This method adds one to the number of coffees and displays it on the screen. It is called
     * when the plus button is clicked.
     */
    public void increment(View view) {
        mQuantity++;
        updateTotal();
        updateScreen(mQuantity, mTotal);
    }

    /**
     * This method subtracts one to the number of coffees and displays it on the screen if the
     * value of quantity is greater than one, else it shows a warning. It is called when the minus
     * button is clicked.
     */
    public void decrement(View view) {
        if (mQuantity > 1) {
            mQuantity--;
            updateTotal();
            updateScreen(mQuantity, mTotal);
        } else {
            Toast toast = Toast.makeText(this, R.string.main_order_warning,
                    Toast.LENGTH_SHORT);
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

    /**
     * This method defines the total String
     */
    private void updateTotal() {
        mTotal = NumberFormat.getCurrencyInstance().format(mQuantity * PRICE);
    }

    /**
     * This method creates a String to be displayed once the user hits the Submit Order button
     *
     * @param pQuantity The quantity selected by the user
     * @param pTotal    The price of the order
     * @return The String to be displayed
     */
    private String createOrderSummary(int pQuantity, String pTotal) {
        return "Name: David Auza" +
                "\nQuantity: " + pQuantity +
                "\nTotal: " + pTotal +
                "\nThank you!";
    }
}