package com.snapcart.data;

import com.snapcart.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private final HashMap<Product, Integer> cartItems;

    private CartManager() {
        cartItems = new HashMap<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product) {
        if (cartItems.containsKey(product)) {
            cartItems.put(product, cartItems.get(product) + 1);
        } else {
            cartItems.put(product, 1);
        }
    }

    public void removeFromCart(Product product) {
        if (cartItems.containsKey(product)) {
            int qty = cartItems.get(product);
            if (qty > 1) {
                cartItems.put(product, qty - 1);
            } else {
                cartItems.remove(product);
            }
        }
    }

    public void increaseQuantity(Product product) {
        if (cartItems.containsKey(product)) {
            cartItems.put(product, cartItems.get(product) + 1);
        }
    }

    public void decreaseQuantity(Product product) {
        if (cartItems.containsKey(product)) {
            int qty = cartItems.get(product);
            if (qty > 1) {
                cartItems.put(product, qty - 1);
            } else {
                cartItems.remove(product);
            }
        }
    }

    public void clearCart() {
        cartItems.clear();
    }

    public HashMap<Product, Integer> getCartItems() {
        return cartItems;
    }

    public List<Product> getCartProductList() {
        List<Product> productList = new ArrayList<>();
        for (Product product : cartItems.keySet()) {
            product.setQuantity(cartItems.get(product)); // ✅ set quantity from the map
            productList.add(product);
        }
        return productList;
    }


    public int getProductQuantity(Product product) {
        return cartItems.getOrDefault(product, 0);
    }

    public double getTotalPrice() {
        double total = 0.0;
        for (Product product : cartItems.keySet()) {
            total += product.getPrice() * cartItems.get(product);
        }
        return total;
    }
}
