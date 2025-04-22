package com.snapcart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    private static final int NOTIFICATION_PERMISSION_CODE = 101;
    private static final String CHANNEL_ID = "snapcart_orders";

    private TextView summaryText;
    private EditText addressInput;
    private Spinner paymentSpinner;
    private Button confirmOrder;
    private ArrayList<Product> currentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        summaryText = findViewById(R.id.text_order_summary);
        addressInput = findViewById(R.id.edit_address);
        paymentSpinner = findViewById(R.id.spinner_payment_method);
        confirmOrder = findViewById(R.id.button_confirm_order);

        currentCart = new ArrayList<>(CartManager.getCartItems());
        double total = 0;
        StringBuilder summary = new StringBuilder("Items:\n\n");

        for (Product item : currentCart) {
            summary.append(item.getName())
                    .append(" × ").append(item.getQuantity())
                    .append(" = $").append(item.getPrice() * item.getQuantity())
                    .append("\n");
            total += item.getPrice() * item.getQuantity();
        }
        summary.append("\nTotal: $").append(total);
        summaryText.setText(summary.toString());

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Credit Card", "Cash on Delivery", "UPI"}
        );
        paymentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentAdapter);

        // 🔐 Ask for POST_NOTIFICATIONS permission (Android 13+)
        requestNotificationPermissionIfNeeded();

        confirmOrder.setOnClickListener(v -> {
            String address = addressInput.getText().toString().trim();
            String payment = paymentSpinner.getSelectedItem().toString();

            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter your delivery address", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔔 Show notification
            showOrderNotification();

            // 🗃️ Save order
            OrderManager.addOrder(new Order(currentCart, address, payment));

            Toast.makeText(this, "✅ Order placed with " + payment + "\nAddress: " + address, Toast.LENGTH_LONG).show();

            CartManager.clearCart(); // 🧹 Clear cart
            finish(); // Go back
        });
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE);
            }
        }
    }

    private void showOrderNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Order Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) manager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, ProductListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("SnapCart")
                .setContentText("✅ Your order has been placed!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat.from(this).notify(1001, builder.build());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Optional: handle permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
