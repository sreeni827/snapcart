package com.snapcart.data.repository;

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
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ProductRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        productDao = db.productDao();
        allProducts = productDao.getAllProductsLive();
    }

    public LiveData<List<ProductEntity>> getAllProducts() {
        return productDao.getAllProductsLive();
    }


    public void insertProduct(ProductEntity product) {
        executorService.execute(() -> productDao.insertProduct(product));
    }

    public void insertAllProducts(List<ProductEntity> products) {
        executorService.execute(() -> productDao.insertAllProducts(products));
    }
}
