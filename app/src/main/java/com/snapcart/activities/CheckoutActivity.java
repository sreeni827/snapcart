package com.snapcart.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.snapcart.R;
import com.snapcart.data.CartManager;
import com.snapcart.data.OrderManager;
import com.snapcart.models.Order;
import com.snapcart.models.Product;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private EditText addressInput;
    private RadioGroup paymentGroup;
    private Button confirmButton;
    private TextView totalText;
    private ArrayList<Product> currentCart;
    private double totalPrice = 0;
    private final String CHANNEL_ID = "order_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar toolbar = findViewById(R.id.checkout_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        addressInput = findViewById(R.id.edit_address);
        paymentGroup = findViewById(R.id.payment_group);
        confirmButton = findViewById(R.id.button_confirm_order);
        totalText = findViewById(R.id.checkout_total);

        currentCart = new ArrayList<>(CartManager.getInstance().getCartProductList());

        for (Product item : currentCart) {
            totalPrice += item.getPrice() * item.getQuantity();
        }

        totalText.setText("Total: $" + String.format("%.2f", totalPrice));

        confirmButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString().trim();
            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter a shipping address.", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedId = paymentGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Select a payment option.", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedPayment = selectedId == R.id.radio_cash ? "Cash" : "Card";

            // ✅ Save Order using new model-based approach
            Order order = new Order(currentCart, address, selectedPayment);
            OrderManager.getInstance().addOrder(order);

            // ✅ Clear cart
            CartManager.getInstance().clearCart();

            // ✅ Show notification
            showOrderNotification();

            // ✅ Go to orders page
            startActivity(new Intent(this, OrdersActivity.class));
            finish();
        });

        createNotificationChannel();
    }

    private void showOrderNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_checkout)
                .setContentTitle("SnapCart")
                .setContentText("Your order has been placed successfully!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1001, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Notifications";
            String description = "Notify users about order confirmation";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
