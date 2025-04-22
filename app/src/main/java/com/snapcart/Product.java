package com.snapcart;

public class Product {
    private String name;
    private double price;
    private int quantity;
    private int imageResId;

    public Product(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;
        this.quantity = 1; // default
    }

    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public int getImageResId() { return imageResId; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
