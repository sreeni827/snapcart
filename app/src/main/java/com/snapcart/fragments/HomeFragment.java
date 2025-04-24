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
import com.snapcart.data.database.ProductEntity;

import com.snapcart.data.CartManager;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductEntity> cartItems;

    private ArrayList<ProductEntity> allProducts;
    private ArrayList<ProductEntity> filteredProducts;


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
        adapter = new ProductAdapter(filteredProducts, getContext());

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
            for (ProductEntity p : allProducts) {
                if (p.getCategory().equalsIgnoreCase(category)) {
                    filteredProducts.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private ArrayList<ProductEntity> getDummyProducts() {
        ArrayList<ProductEntity> list = new ArrayList<>();
        list.add(new ProductEntity("Wireless Headphones", 59.99, 1, R.drawable.headphones, "Accessories"));
        list.add(new ProductEntity("Smartphone", 499.00, 1, R.drawable.smartphone, "Phones"));
        list.add(new ProductEntity("Fitness Watch", 99.99, 1, R.drawable.smartwatch, "Watches"));
        list.add(new ProductEntity("Bluetooth Speaker", 39.99, 1, R.drawable.speaker, "Accessories"));
        list.add(new ProductEntity("Laptop", 799.00, 1, R.drawable.laptop, "Laptops"));
        list.add(new ProductEntity("VR Headset", 149.00, 1, R.drawable.ic_placeholder, "Accessories"));
        return list;
    }
}
