package com.snapcart.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.adapters.OrderAdapter;
import com.snapcart.data.OrderManager;

public class OrdersActivity extends AppCompatActivity {

    private RecyclerView ordersRecyclerView;
    private TextView emptyOrdersText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        Toolbar toolbar = findViewById(R.id.orders_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        ordersRecyclerView = findViewById(R.id.orders_recycler_view);
        emptyOrdersText = findViewById(R.id.text_no_orders);

        if (OrderManager.getInstance().getOrders().isEmpty()) {
            emptyOrdersText.setVisibility(TextView.VISIBLE);
            ordersRecyclerView.setVisibility(RecyclerView.GONE);
        } else {
            emptyOrdersText.setVisibility(TextView.GONE);
            ordersRecyclerView.setVisibility(RecyclerView.VISIBLE);
            ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            ordersRecyclerView.setAdapter(new OrderAdapter(OrderManager.getInstance().getOrders()));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
