package com.snapcart.activities;

import android.os.Bundle;
import android.widget.TextView;
import com.snapcart.models.Order;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.snapcart.R;
import com.snapcart.models.Order;
import com.snapcart.models.Product;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView summaryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        // Toolbar with back
        Toolbar toolbar = findViewById(R.id.order_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        summaryText = findViewById(R.id.order_detail_summary);

        // Get order from intent
        String json = getIntent().getStringExtra("order");
        Order order = new Gson().fromJson(json, Order.class);

        StringBuilder summary = new StringBuilder("📦 Order Summary\n\n");

        for (Product p : order.getProducts()) {
            summary.append("• ").append(p.getName())
                    .append(" × ").append(p.getQuantity())
                    .append(" = $").append(String.format("%.2f", p.getPrice() * p.getQuantity()))
                    .append("\n");
        }

        summary.append("\nTotal: $").append(String.format("%.2f", order.getTotal()))
                .append("\n\nDelivery: ").append(order.getAddress())
                .append("\nPayment: ").append(order.getPaymentMethod());

        summaryText.setText(summary.toString());
    }
}
