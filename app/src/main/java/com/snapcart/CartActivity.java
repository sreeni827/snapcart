package com.snapcart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView totalAmountText, emptyMessage;
    private Button checkoutButton;
    private CartAdapter cartAdapter;
    private ArrayList<Product> cartList = new ArrayList<>();
    private double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // ❌ This line causes crash with NoActionBar theme
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        totalAmountText = findViewById(R.id.cart_total);
        emptyMessage = findViewById(R.id.text_cart_empty);
        checkoutButton = findViewById(R.id.button_checkout);

        cartList = CartManager.getCartItems();

        cartAdapter = new CartAdapter(cartList, this::calculateTotal);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        // 👁️ Show or hide empty cart message
        if (cartList.isEmpty()) {
            emptyMessage.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
            checkoutButton.setVisibility(View.GONE);
            totalAmountText.setVisibility(View.GONE);
        } else {
            emptyMessage.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            checkoutButton.setVisibility(View.VISIBLE);
            totalAmountText.setVisibility(View.VISIBLE);
        }

        calculateTotal();

        checkoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CheckoutActivity.class));
        });
    }

    private void calculateTotal() {
        totalAmount = 0;
        for (Product item : cartList) {
            totalAmount += item.getPrice() * item.getQuantity();
        }
        totalAmountText.setText("Total: $" + String.format("%.2f", totalAmount));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
