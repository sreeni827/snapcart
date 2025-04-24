package com.snapcart.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.data.database.ProductEntity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductEntity> productList;
    private Context context;

    public ProductAdapter(List<ProductEntity> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void updateData(List<ProductEntity> newList) {
        productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductEntity p = productList.get(position);
        holder.name.setText(p.getTitle());
        holder.price.setText("$" + String.format("%.2f", p.getPrice()));
        holder.image.setImageResource(p.getImageResId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_product_name);
            price = itemView.findViewById(R.id.text_product_price);
            image = itemView.findViewById(R.id.image_product);
        }
    }
}
