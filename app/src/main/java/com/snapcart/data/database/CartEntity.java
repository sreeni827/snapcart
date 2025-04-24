package com.snapcart.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_items")
public class CartEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productName;
    public String title;
    public double price;
    public int quantity;
    public int imageResId;
    public String category;

    public CartEntity(String productName, double price, int quantity, int imageResId, String category) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }
    public String getCategory() { return category; }
}
