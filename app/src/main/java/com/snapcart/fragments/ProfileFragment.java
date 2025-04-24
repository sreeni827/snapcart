package com.snapcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.snapcart.R;
import com.snapcart.data.OrderManager;
import com.snapcart.models.Order;
import com.snapcart.data.database.ProductEntity;


import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView userNameText, emailText;
    private TextView ordersHeading;
    private LinearLayout ordersDetailsLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userNameText = view.findViewById(R.id.text_user_name);
        emailText = view.findViewById(R.id.text_email);
        ordersHeading = view.findViewById(R.id.orders_heading);
        ordersDetailsLayout = view.findViewById(R.id.orders_details_layout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            String userName = user.getDisplayName();

            emailText.setText(userEmail != null ? userEmail : "user@example.com");
            userNameText.setText(userName != null ? userName : "SnapCart User");
        } else {
            emailText.setText("user@example.com");
            userNameText.setText("SnapCart User");
        }

        ordersHeading.setOnClickListener(v -> {
            if (ordersDetailsLayout.getVisibility() == View.GONE) {
                populateOrders();
                ordersDetailsLayout.setVisibility(View.VISIBLE);
            } else {
                ordersDetailsLayout.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void populateOrders() {
        ordersDetailsLayout.removeAllViews();
        List<Order> orders = OrderManager.getInstance().getOrders();
        for (Order order : orders) {
            TextView orderView = new TextView(getContext());
            orderView.setText(
                    "Address: " + order.getAddress() +
                            "\nPayment: " + order.getPaymentMethod() +
                            "\nTotal: $" + String.format("%.2f", order.getTotal()) +
                            "\nItems:\n" + getItemsSummary(order.getProducts()) +
                            "\n---------------------"
            );
            orderView.setPadding(10, 10, 10, 10);
            ordersDetailsLayout.addView(orderView);
        }
    }

    private String getItemsSummary(List<ProductEntity> products) {
        StringBuilder builder = new StringBuilder();
        for (ProductEntity product : products) {
            builder.append("- ").append(product.getTitle())
                    .append(" x").append(product.getQuantity())
                    .append("\n");
        }
        return builder.toString();
    }
}
