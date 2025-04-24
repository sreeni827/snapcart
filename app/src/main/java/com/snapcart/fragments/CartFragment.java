package com.snapcart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.activities.CheckoutActivity;
import com.snapcart.adapters.CartAdapter;
import com.snapcart.data.CartManager;
import com.snapcart.models.Product;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private TextView totalAmountTextView;
    private Button checkoutButton;
    private List<Product> cartItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        totalAmountTextView = view.findViewById(R.id.total_amount_text_view);
        checkoutButton = view.findViewById(R.id.checkout_button);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartItems = CartManager.getInstance().getCartProductList();
        CartAdapter adapter = new CartAdapter(cartItems, requireContext(), this::updateTotalAmount);
        cartRecyclerView.setAdapter(adapter);

        updateTotalAmount(); // Initialize total

        checkoutButton.setOnClickListener(v -> {
            if (!CartManager.getInstance().getCartItems().isEmpty()) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            } else {
                Toast.makeText(getActivity(), "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void updateTotalAmount() {
        double total = 0.0;
        for (Product p : cartItems) {
            total += p.getPrice() * p.getQuantity();
        }
        totalAmountTextView.setText("Total: $" + String.format("%.2f", total));
    }
}
