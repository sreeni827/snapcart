package com.snapcart.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.snapcart.R;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.models.Product;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private TextInputEditText searchInput;
    private RecyclerView searchRecyclerView;
    private TextView searchHintText;
    private ProductAdapter adapter;
    private ArrayList<Product> allProducts;
    private ArrayList<Product> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchInput = view.findViewById(R.id.search_edit_text);
        searchRecyclerView = view.findViewById(R.id.search_recycler_view);
        searchHintText = view.findViewById(R.id.search_hint_text);

        allProducts = getAllProducts(); // dummy list
        filteredList = new ArrayList<>();

        adapter = new ProductAdapter(filteredList, product -> {
            // Optional: Add to cart or navigate
        });

        searchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRecyclerView.setAdapter(adapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }
        });

        return view;
    }

    private void filterProducts(String query) {
        filteredList.clear();

        if (query.isEmpty()) {
            searchHintText.setVisibility(View.VISIBLE);
            searchRecyclerView.setVisibility(View.GONE);
            return;
        }

        for (Product p : allProducts) {
            if (p.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(p);
            }
        }

        adapter.notifyDataSetChanged();
        searchHintText.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        searchRecyclerView.setVisibility(filteredList.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("Smartphone", "Phones", 499.00, R.drawable.smartphone));
        list.add(new Product("Wireless Headphones", "Accessories", 59.99, R.drawable.headphones));
        list.add(new Product("Laptop", "Laptops", 899.00, R.drawable.laptop));
        list.add(new Product("Bluetooth Speaker", "Accessories", 39.99, R.drawable.speaker));
        return list;
    }
}
