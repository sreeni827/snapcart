package com.snapcart.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.snapcart.R;
import com.snapcart.data.ProductRepository;
import com.snapcart.data.database.ProductEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private final ProductRepository repository;
    private final LiveData<List<ProductEntity>> allProducts;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(ProductEntity product) {
        repository.insertProduct(product);
    }

    public void insertAll(List<ProductEntity> products) {
        repository.insertAllProducts(products);
    }

    public void insertSampleProductsIfEmpty(Context context) {
        getAllProducts().observeForever(products -> {
            if (products == null || products.isEmpty()) {
                List<ProductEntity> sampleProducts = new ArrayList<>();
                sampleProducts.add(new ProductEntity("Wireless Headphones", 59.99, 1, R.drawable.headphones, "Accessories"));
                sampleProducts.add(new ProductEntity("Smartphone", 499.00, 1, R.drawable.smartphone, "Phones"));
                sampleProducts.add(new ProductEntity("Fitness Watch", 99.99, 1, R.drawable.smartwatch, "Watches"));
                sampleProducts.add(new ProductEntity("Bluetooth Speaker", 39.99, 1, R.drawable.speaker, "Accessories"));
                insertAll(sampleProducts);
            }
        });
    }
}
