package com.snapcart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.snapcart.data.CartManager;
import com.snapcart.models.Product;
import com.snapcart.adapters.ProductAdapter;
import com.snapcart.R;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;

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

        productList = getDummyProducts();

        adapter = new ProductAdapter(productList, product -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(this, product.getTitle() + " added to cart!", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Product> getDummyProducts() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("Wireless Headphones", "Accessories", 59.99, R.drawable.headphones));
        list.add(new Product("Smartphone", "Phones", 499.00, R.drawable.smartphone));
        list.add(new Product("Fitness Watch", "Watches", 99.99, R.drawable.smartwatch));
        list.add(new Product("Bluetooth Speaker", "Accessories", 39.99, R.drawable.speaker));
        return list;
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
