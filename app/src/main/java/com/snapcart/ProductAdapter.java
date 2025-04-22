package com.snapcart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final ArrayList<Product> productList;
    private final OnAddToCartListener listener;
    private final Context context;

    public interface OnAddToCartListener {
        void onAdd(Product product);
    }

    public ProductAdapter(ArrayList<Product> productList, OnAddToCartListener listener) {
        this.productList = productList;
        this.listener = listener;
        this.context = null;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        // ✅ Bind views here
        ImageView image = holder.itemView.findViewById(R.id.product_image);
        TextView name = holder.itemView.findViewById(R.id.product_name);
        TextView price = holder.itemView.findViewById(R.id.product_price);
        Button addToCart = holder.itemView.findViewById(R.id.add_to_cart);

        image.setImageResource(product.getImageResId()); // Assuming drawable resource
        name.setText(product.getName());
        price.setText("$" + product.getPrice());

        addToCart.setOnClickListener(v -> {
            product.setQuantity(product.getQuantity() + 1); // Optional
            listener.onAdd(product);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
