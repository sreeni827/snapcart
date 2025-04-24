package com.snapcart.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.adapters.CartAdapter;
import com.snapcart.data.CartManager;
import com.snapcart.data.database.ProductEntity;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private TextView emptyCartText, totalTextView;
    private Button checkoutButton;
    private CartAdapter cartAdapter;
    private List<ProductEntity> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecyclerView = findViewById(R.id.cart_recycler_view);
        emptyCartText = findViewById(R.id.text_cart_empty);
        totalTextView = findViewById(R.id.cart_total);
        checkoutButton = findViewById(R.id.button_checkout);

        cartList = CartManager.getInstance().getCartItemsAsList();
        cartAdapter = new CartAdapter(cartList, this, this::updateCartUI);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        updateCartUI();

        checkoutButton.setOnClickListener(v -> {
            // You can navigate to a CheckoutActivity or show a dialog
            CartManager.getInstance().clearCart();
            updateCartUI();
        });
    }

    private void updateCartUI() {
        if (cartList.isEmpty()) {
            emptyCartText.setVisibility(View.VISIBLE);
            cartRecyclerView.setVisibility(View.GONE);
            checkoutButton.setEnabled(false);
        } else {
            emptyCartText.setVisibility(View.GONE);
            cartRecyclerView.setVisibility(View.VISIBLE);
            checkoutButton.setEnabled(true);
        }

        double total = 0;
        for (ProductEntity product : cartList) {
            total += product.getPrice() * product.getQuantity();
        }
        totalTextView.setText(String.format("Total: $%.2f", total));
    }
}
