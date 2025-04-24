package com.snapcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.data.CartManager;
import com.snapcart.data.database.CartEntity;
import com.snapcart.data.database.ProductEntity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final List<ProductEntity> cartList;
    private final Context context;
    private final Runnable onCartChanged;

    public CartAdapter(List<ProductEntity> cartList, Context context, Runnable onCartChanged) {
        this.cartList = cartList;
        this.context = context;
        this.onCartChanged = onCartChanged;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductEntity product = cartList.get(position);

        holder.titleTextView.setText(product.getTitle());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.quantityTextView.setText(String.valueOf(product.getQuantity()));
        holder.imageView.setImageResource(product.getImageResId());

        holder.increaseButton.setOnClickListener(v -> {
            CartManager.getInstance().increaseQuantity(product);
            notifyItemChanged(position);
            onCartChanged.run();
        });

        holder.decreaseButton.setOnClickListener(v -> {
            CartManager.getInstance().decreaseQuantity(product);
            if (product.getQuantity() <= 0) {
                cartList.remove(position);
                notifyItemRemoved(position);
            } else {
                notifyItemChanged(position);
            }
            onCartChanged.run();
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, priceTextView, quantityTextView;
        ImageView imageView;
        ImageButton increaseButton, decreaseButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.cart_item_title);
            priceTextView = itemView.findViewById(R.id.cart_item_price);
            quantityTextView = itemView.findViewById(R.id.cart_item_quantity);
            imageView = itemView.findViewById(R.id.cart_item_image);
            increaseButton = itemView.findViewById(R.id.button_increase);
            decreaseButton = itemView.findViewById(R.id.button_decrease);
        }
    }

    public List<CartEntity> getItems() {
        return cartItems;
    }

}
