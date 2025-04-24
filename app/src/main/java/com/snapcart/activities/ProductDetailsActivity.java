package com.snapcart.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.snapcart.R;
import com.snapcart.data.database.ProductEntity;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView titleTextView, priceTextView, categoryTextView;
    private ImageView productImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        titleTextView = findViewById(R.id.titleTextView);
        priceTextView = findViewById(R.id.priceTextView);
        categoryTextView = findViewById(R.id.categoryTextView);
        productImageView = findViewById(R.id.productImageView);

        ProductEntity product = (ProductEntity) getIntent().getSerializableExtra("product");

        if (product != null) {
            titleTextView.setText(product.getTitle());
            priceTextView.setText("$" + product.getPrice());
            categoryTextView.setText(product.getCategory());
            productImageView.setImageResource(product.getImageResId());
        }
    }
}
