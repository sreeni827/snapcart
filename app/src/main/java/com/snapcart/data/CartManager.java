package com.snapcart.data;

import com.snapcart.data.database.CartEntity;
import com.snapcart.data.database.ProductEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartManager {

    private static CartManager instance;
    private final HashMap<ProductEntity, Integer> cartItems = new HashMap<>();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(ProductEntity product) {
        int quantity = cartItems.getOrDefault(product, 0);
        cartItems.put(product, quantity + 1);
        product.setQuantity(quantity + 1);
    }

    public void increaseQuantity(ProductEntity product) {
        int quantity = cartItems.getOrDefault(product, 0);
        cartItems.put(product, quantity + 1);
        product.setQuantity(quantity + 1);
    }

    public void decreaseQuantity(ProductEntity product) {
        int quantity = cartItems.getOrDefault(product, 0);
        if (quantity > 1) {
            cartItems.put(product, quantity - 1);
            product.setQuantity(quantity - 1);
        } else {
            cartItems.remove(product);
            product.setQuantity(0);
        }
    }

    public void clearCart() {
        cartItems.clear();
    }

    // ✅ Fix for your issue — return a list of products
    public List<ProductEntity> getCartItemsAsList() {
        List<ProductEntity> list = new ArrayList<>();
        for (ProductEntity product : cartItems.keySet()) {
            product.setQuantity(cartItems.get(product)); // ensure quantity is up-to-date
            list.add(product);
        }
        return list;
    }
    public List<CartEntity> getCartProductList() {
        List<CartEntity> cartEntityList = new ArrayList<>();
        for (Map.Entry<ProductEntity, Integer> entry : cartItems.entrySet()) {
            ProductEntity product = entry.getKey();
            int quantity = entry.getValue();
            CartEntity entity = new CartEntity(product.getTitle(), product.getPrice(), quantity);
            cartEntityList.add(entity);
        }
        return cartEntityList;
    }


    public double getTotalAmount() {
        double total = 0.0;
        for (ProductEntity product : cartItems.keySet()) {
            total += product.getPrice() * cartItems.get(product);
        }
        return total;
    }
}
