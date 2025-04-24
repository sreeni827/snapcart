package com.snapcart.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.data.database.ProductEntity;

import com.snapcart.data.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductEntity> allProducts;

    private EditText searchInput;
    private Spinner categorySpinner;
    private ProductRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchInput = view.findViewById(R.id.edit_search);
        categorySpinner = view.findViewById(R.id.spinner_category);

        repository = new ProductRepository(requireActivity().getApplication());
        allProducts = repository.getAllProducts().getValue(); // ✅ fix
        if (allProducts == null) {
            allProducts = new ArrayList<>(); // avoid null crash
        } // Replace with your data source
        adapter = new ProductAdapter(allProducts, requireContext());
        recyclerView.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(), R.array.categories_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts();
            }
            public void afterTextChanged(Editable s) {}
        });

        categorySpinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                filterProducts();
            }
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        return view;
    }

    private void filterProducts() {
        String query = searchInput.getText().toString().toLowerCase();
        String selectedCategory = categorySpinner.getSelectedItem().toString();

        List<ProductEntity> filtered = new ArrayList<>();
        for (ProductEntity product : allProducts) {
            boolean matchesSearch = product.getTitle().toLowerCase().contains(query);
            boolean matchesCategory = selectedCategory.equals("All") || product.getCategory().equalsIgnoreCase(selectedCategory);
            if (matchesSearch && matchesCategory) {
                filtered.add(product);
            }
        }

        adapter.updateData(filtered);
    }
}
