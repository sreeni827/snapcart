package com.snapcart.models;

public class Product {
    private String title;
    private String category;
    private double price;
    private int imageRes;
    private int quantity = 1; // ✅ Added field with default quantity

    public Product(String title, String category, double price, int imageRes) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.imageRes = imageRes;
        this.quantity = 1;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getImageRes() {
        return imageRes;
    }

    public int getQuantity() {
        return quantity;
    }
    public String getName() { return title; }

    public int getImageResId() { return imageRes; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return title.equals(product.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
