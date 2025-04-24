package com.snapcart.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCart(CartEntity cartEntity);

    @Delete
    void deleteCart(CartEntity cartEntity);

    @Update
    void updateCart(CartEntity cartEntity);

    @Query("DELETE FROM cart_items")
    void clearCart();

    @Query("SELECT * FROM cart_items")
    LiveData<List<CartEntity>> getAllCartItems();
}

