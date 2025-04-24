package com.snapcart.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.snapcart.R;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.models.Product;
import com.snapcart.data.CartManager;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> allProducts;
    private ArrayList<Product> filteredProducts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        EditText searchBar = view.findViewById(R.id.edit_search);
        recyclerView = view.findViewById(R.id.home_recycler_view);
        ChipGroup chipGroup = view.findViewById(R.id.chip_group);

        allProducts = getDummyProducts();
        filteredProducts = new ArrayList<>(allProducts);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new ProductAdapter(filteredProducts, product -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(getContext(), product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = group.findViewById(checkedId);
            if (selectedChip != null) {
                filterProducts(selectedChip.getText().toString());
            }
        });

        return view;
    }

    private void filterProducts(String category) {
        filteredProducts.clear();
        if (category.equals("All")) {
            filteredProducts.addAll(allProducts);
        } else {
            for (Product p : allProducts) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    filteredProducts.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private ArrayList<Product> getDummyProducts() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("Wireless Headphones", "Accessories", 59.99, R.drawable.headphones));
        list.add(new Product("Smartphone", "Phones", 499.00, R.drawable.smartphone));
        list.add(new Product("Fitness Watch", "Watches", 99.99, R.drawable.smartwatch));
        list.add(new Product("Bluetooth Speaker", "Accessories", 39.99, R.drawable.speaker));
        list.add(new Product("Laptop", "Laptops", 799.00, R.drawable.laptop));
        list.add(new Product("VR Headset", "Accessories", 149.00, R.drawable.ic_placeholder));
        return list;
    }
}
