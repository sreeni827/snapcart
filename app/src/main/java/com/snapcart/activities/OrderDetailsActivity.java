package com.snapcart.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.snapcart.R;
import com.snapcart.data.database.AppDatabase;
import com.snapcart.data.database.OrderEntity;

import java.util.List;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView summaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        summaryTextView = findViewById(R.id.order_detail_summary);

        new Thread(() -> {
            List<OrderEntity> orders = AppDatabase.getInstance(this).orderDao().getAllOrders();
            if (orders != null && !orders.isEmpty()) {
                OrderEntity latestOrder = orders.get(0);
                String summary = "🛍 Items:\n" + latestOrder.itemsSummary + "\n\n"
                        + "💰 Total: $" + latestOrder.totalAmount + "\n"
                        + "🏠 Address: " + latestOrder.address + "\n"
                        + "💳 Payment: " + latestOrder.paymentMethod + "\n"
                        + "📅 Date: " + latestOrder.date;

                runOnUiThread(() -> summaryTextView.setText(summary));
            } else {
                runOnUiThread(() -> summaryTextView.setText("No recent orders found."));
            }
        }).start();
    }
}
