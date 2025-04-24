package com.snapcart.models;

import com.snapcart.data.database.ProductEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Order {
    private List<ProductEntity> products;
    private String address;
    private String paymentMethod;

    public Order(List<ProductEntity> products, String address, String paymentMethod) {
        this.products = products;
        this.address = address;
        this.paymentMethod = paymentMethod;
    }

    public List<ProductEntity> getProducts() {
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
        for (ProductEntity p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    public double getTotalPrice() {
        return getTotal();
    }

    public String getDate() {
        return new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault()).format(new Date());
    }
}
