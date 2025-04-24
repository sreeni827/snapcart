package com.snapcart.models;

import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order {
    private List<Product> products;
    private String address;
    private String paymentMethod;

    public Order(List<Product> products, String address, String paymentMethod) {
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getAddress() {
        return address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getTotal() {
        double total = 0.0;
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    public String getDate() {
        return new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(new Date());
    }

    public double getTotalPrice() {
        return getTotal();
    }

}
