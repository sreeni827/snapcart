package com.snapcart.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.snapcart.data.database.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(ProductEntity product);

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProductsLive();


    @Query("DELETE FROM products")
    void deleteAll();
}
