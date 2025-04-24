package com.snapcart.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.snapcart.data.CartRepository;
import com.snapcart.data.database.CartEntity;

import java.util.List;
public class CartViewModel extends AndroidViewModel {
    private final CartRepository repository;
    private final LiveData<List<CartEntity>> allCartItems;

    public CartViewModel(@NonNull Application application) {
        super(application);
        repository = new CartRepository(application);
        allCartItems = repository.getAllCartItems();
    }

    public void insert(CartEntity cartEntity) {
        repository.insert(cartEntity);
    }

    public void delete(CartEntity cartEntity) {
        repository.delete(cartEntity);
    }

    public void update(CartEntity cartEntity) {
        repository.update(cartEntity);
    }

    public void clearCart() {
        repository.clearCart();
    }

    public LiveData<List<CartEntity>> getCartItems() {
        return allCartItems;
    }
}
