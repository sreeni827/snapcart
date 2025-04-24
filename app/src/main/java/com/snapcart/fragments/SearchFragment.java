package com.snapcart.fragments;

import android.content.Intent;
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
import com.snapcart.activities.ProductDetailsActivity;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.data.database.ProductEntity;


import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private TextInputEditText searchInput;
    private RecyclerView searchRecyclerView;
    private TextView searchHintText;
    private ProductAdapter adapter;
    private ArrayList<ProductEntity> allProducts;
    private ArrayList<ProductEntity> filteredList;

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

        adapter = new ProductAdapter(filteredList, getContext());




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

        for (ProductEntity p : allProducts) {
            if (p.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(p);
            }
        }

        adapter.notifyDataSetChanged();
        searchHintText.setVisibility(filteredList.isEmpty() ? View.VISIBLE : View.GONE);
        searchRecyclerView.setVisibility(filteredList.isEmpty() ? View.GONE : View.VISIBLE);
    }

    private ArrayList<ProductEntity> getAllProducts() {
        ArrayList<ProductEntity> list = new ArrayList<>();
        list.add(new ProductEntity("Smartphone", 499.00, 1, R.drawable.smartphone, "Phones"));
        list.add(new ProductEntity("Wireless Headphones", 59.99, 1, R.drawable.headphones, "Accessories"));
        list.add(new ProductEntity("Laptop", 899.00, 1, R.drawable.laptop, "Laptops"));
        list.add(new ProductEntity("Bluetooth Speaker", 39.99, 1, R.drawable.speaker, "Accessories"));
        return list;
    }
}
