package com.snapcart;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // ✅ Set up the toolbar manually
        Toolbar toolbar = findViewById(R.id.orders_toolbar);
        setSupportActionBar(toolbar);

        // ✅ Enable back/home button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    // ✅ Handle toolbar back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // go back
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
