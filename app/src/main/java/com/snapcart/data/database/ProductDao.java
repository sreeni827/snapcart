package com.snapcart.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.snapcart.data.database.ProductEntity;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insertProduct(ProductEntity product);

    @Insert
    void insertAllProducts(List<ProductEntity> products);

    @Update
    void updateProduct(ProductEntity product);

    @Delete
    void deleteProduct(ProductEntity product);

    @Query("SELECT * FROM products")
    LiveData<List<ProductEntity>> getAllProductsLive();

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    ProductEntity getProductById(int id);
}
