package com.snapcart.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import com.snapcart.R;
import com.snapcart.data.AppDatabase;
import com.snapcart.data.CartManager;
import com.snapcart.data.database.OrderEntity;
import com.snapcart.data.database.ProductEntity;

import java.util.Date;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private EditText editAddress;
    private RadioGroup paymentGroup;
    private TextView totalTextView;
    private Button confirmButton;

    private static final String CHANNEL_ID = "snapcart_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        editAddress = findViewById(R.id.edit_address);
        paymentGroup = findViewById(R.id.payment_group);
        totalTextView = findViewById(R.id.checkout_total);
        confirmButton = findViewById(R.id.button_confirm_order);

        createNotificationChannel();
        updateTotalAmount();

        confirmButton.setOnClickListener(v -> {
            String address = editAddress.getText().toString().trim();
            int selectedId = paymentGroup.getCheckedRadioButtonId();

            if (address.isEmpty()) {
                Toast.makeText(this, "Please enter a delivery address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedId == -1) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedPayment = findViewById(selectedId);
            String paymentMethod = selectedPayment.getText().toString();

            List<ProductEntity> cartItems = CartManager.getInstance().getCartItemsAsList();

            StringBuilder itemsSummary = new StringBuilder();
            double total = 0.0;
            for (ProductEntity product : cartItems) {
                itemsSummary.append(product.getTitle())
                        .append(" x")
                        .append(product.getQuantity())
                        .append("\n");
                total += product.getPrice() * product.getQuantity();
            }

            OrderEntity order = new OrderEntity(
                    itemsSummary.toString().trim(),
                    total,
                    address,
                    paymentMethod,
                    new Date()
            );

            AppDatabase.databaseWriteExecutor.execute(() -> {
                AppDatabase.getInstance(this).orderDao().insertOrder(order);

                runOnUiThread(() -> {
                    showNotification();
                    Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().clearCart();
                    startActivity(new Intent(this, OrderDetailsActivity.class));
                    finish();
                });
            });
        });
    }

    private void updateTotalAmount() {
        double total = 0.0;
        List<ProductEntity> cartItems = CartManager.getInstance().getCartItemsAsList();
        for (ProductEntity product : cartItems) {
            total += product.getPrice() * product.getQuantity();
        }
        totalTextView.setText(String.format("Total: $%.2f", total));
    }

    private void showNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Built-in Android icon

                .setContentTitle("Order Confirmed!")
                .setContentText("Your SnapCart order has been placed successfully.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1001, builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "SnapCart Channel";
            String description = "Channel for order notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
