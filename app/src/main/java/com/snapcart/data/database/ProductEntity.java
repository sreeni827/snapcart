package com.snapcart.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products")
public class ProductEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private double price;
    private int quantity;
    private int imageResId;
    private String category;

    public ProductEntity(String title, double price, int quantity, int imageResId, String category) {
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.imageResId = imageResId;
        this.category = category;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getCategory() {
        return category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
