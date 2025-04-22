package com.snapcart;

import java.util.List;

public class Order {
    public List<Product> products;
    public String address;
    public String paymentMethod;

    public Order(List<Product> products, String address, String paymentMethod) {
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }
}
