package com.snapcart.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.snapcart.data.database.AppDatabase;
import com.snapcart.data.database.CartDao;
import com.snapcart.data.database.CartEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CartRepository {
    private final CartDao cartDao;
    private final ExecutorService executorService;

    public CartRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        cartDao = db.cartDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(CartEntity item) {
        executorService.execute(() -> cartDao.insertCart(item));
    }

    public void delete(CartEntity item) {
        executorService.execute(() -> cartDao.deleteCart(item));
    }

    public void update(CartEntity item) {
        executorService.execute(() -> cartDao.updateCart(item));
    }

    public void clearCart() {
        executorService.execute(() -> cartDao.clearCart());
    }


    public LiveData<List<CartEntity>> getAllCartItems() {
        return cartDao.getAllCartItems();
    }
}

