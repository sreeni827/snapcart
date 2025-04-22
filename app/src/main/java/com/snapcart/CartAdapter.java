package com.snapcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Product> cartItems;
    private OnCartUpdatedListener listener;

    public interface OnCartUpdatedListener {
        void onCartUpdated();
    }

    public CartAdapter(ArrayList<Product> cartItems, OnCartUpdatedListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.title.setText(product.getName());
        holder.price.setText("$" + product.getPrice());
        holder.quantity.setText("Qty: " + product.getQuantity());
        holder.image.setImageResource(product.getImageResId());

        holder.increase.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1);
            notifyItemChanged(position);
            listener.onCartUpdated();
        });

        holder.decrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                product.setQuantity(product.getQuantity() - 1);
                notifyItemChanged(position);
                listener.onCartUpdated();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView title, price, quantity;
        ImageView image;
        Button increase, decrease;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cart_product_title);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_product_quantity);
            image = itemView.findViewById(R.id.cart_product_image);
            increase = itemView.findViewById(R.id.button_increase);
            decrease = itemView.findViewById(R.id.button_decrease);
        }
    }
}
