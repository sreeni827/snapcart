package com.snapcart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.snapcart.R;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.data.database.ProductEntity;
import com.snapcart.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductEntity> productList = new ArrayList<>();
    private ProductViewModel productViewModel;

    private List<ProductEntity> getDummyProducts() {
        List<ProductEntity> list = new ArrayList<>();
        list.add(new ProductEntity("Wireless Headphones", 59.99, 1, R.drawable.headphones, "Accessories"));
        list.add(new ProductEntity("Smartphone", 499.00, 1, R.drawable.smartphone, "Phones"));
        list.add(new ProductEntity("Fitness Watch", 99.99, 1, R.drawable.smartwatch, "Watches"));
        list.add(new ProductEntity("Bluetooth Speaker", 39.99, 1, R.drawable.speaker, "Accessories"));
        return list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth.getInstance().signOut();

        setContentView(R.layout.activity_product_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("SnapCart");

        recyclerView = findViewById(R.id.recycler_view_products);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProductAdapter(productList, this);
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // 🔁 Observe product data
        productViewModel.getAllProducts().observe(this, products -> {
            productList.clear();
            productList.addAll(products);
            adapter.updateData(productList);
        });

        // 🔁 Insert dummy products only once (if DB is empty)
        productViewModel.getAllProducts().observe(this, products -> {
            if (products == null || products.isEmpty()) {
                productViewModel.insertAll(getDummyProducts());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_orders) {
            startActivity(new Intent(this, OrdersActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
