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

    int quantity = 1;
    final int PRICE = 5;
    String total;
    TextView quantityTextView;
    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quantityTextView = findViewById(R.id.quantity_text_view);
        priceTextView = findViewById(R.id.price_text_view);
        updateTotal();
        updateScreen(quantity, total);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        String priceMessage = "Total: " + total + "\nThank you!";
        priceTextView.setText(priceMessage);
    }

    /**
     * This method updates the values of quantity and total in the screen
     */
    private void updateScreen(int quantity, String total) {
        quantityTextView.setText(Integer.toString(quantity));
        priceTextView.setText(total);
    }

    /**
     * This method adds one to the number of coffees and displays it on the screen. It is called
     * when the plus button is clicked.
     */
    public void increment(View view) {
        quantity++;
        updateTotal();
        updateScreen(quantity, total);
    }

    /**
     * This method subtracts one to the number of coffees and displays it on the screen if the
     * value of quantity is greater than one, else it shows a warning. It is called when the minus
     * button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity--;
            updateTotal();
            updateScreen(quantity, total);
        } else {
            Toast toast = Toast.makeText(this, R.string.order_warning,
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
        total = NumberFormat.getCurrencyInstance().format(quantity * PRICE);
    }
}