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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.activities.CheckoutActivity;
import com.snapcart.adapters.CartAdapter;
import com.snapcart.data.database.CartEntity;
import com.snapcart.viewmodel.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private TextView totalAmountTextView;
    private Button checkoutButton;
    private CartAdapter adapter;
    private CartViewModel cartViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        totalAmountTextView = view.findViewById(R.id.total_amount_text_view);
        checkoutButton = view.findViewById(R.id.checkout_button);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), new Observer<List<CartEntity>>() {
            @Override
            public void onChanged(List<CartEntity> cartEntities) {
                adapter = new CartAdapter(cartEntities, requireContext(), CartFragment.this::updateTotalAmount);
                cartRecyclerView.setAdapter(adapter);
                updateTotalAmount(cartEntities);
            }
        });

        checkoutButton.setOnClickListener(v -> {
            if (adapter != null && adapter.getItemCount() > 0) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            } else {
                Toast.makeText(getActivity(), "Cart is empty", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void updateTotalAmount() {
        double total = 0.0;
        if (adapter != null) {
            for (CartEntity item : adapter.getItems()) {
                total += item.getPrice() * item.getQuantity();
            }
        }
        totalAmountTextView.setText("Total: $" + String.format("%.2f", total));
    }

}
