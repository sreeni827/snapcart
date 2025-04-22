package com.snapcart;

import java.util.ArrayList;

public class CartManager {
    private static ArrayList<Product> cartItems = new ArrayList<>();

    public static void addToCart(Product product) {
        cartItems.add(product);
    }

    public static ArrayList<Product> getCartItems() {
        return cartItems;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
