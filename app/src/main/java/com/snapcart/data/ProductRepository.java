package com.snapcart.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.snapcart.data.database.AppDatabase;
import com.snapcart.data.database.ProductDao;
import com.snapcart.data.database.ProductEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductRepository {

    private final ProductDao productDao;
    private final LiveData<List<ProductEntity>> allProducts;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProductsLive();  // ✅ Must be LiveData<List<ProductEntity>>
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return allProducts;
    }

    public void insertProduct(ProductEntity product) {
        databaseWriteExecutor.execute(() -> {
            productDao.insertProduct(product);
        });
    }

    public void insertAllProducts(List<ProductEntity> products) {
        databaseWriteExecutor.execute(() -> {
            productDao.insertAllProducts(products);
        });
    }
}
