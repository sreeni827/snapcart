package com.snapcart.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapcart.R;
import com.snapcart.models.Order;
import com.snapcart.models.Product;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orders;

    public OrderAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderDate.setText(order.getDate());

        StringBuilder items = new StringBuilder();
        for (Product p : order.getProducts()) {
            items.append(p.getTitle())
                    .append(" × ")
                    .append(p.getQuantity())
                    .append("\n");
        }

        holder.orderItems.setText(items.toString().trim());
        holder.orderTotal.setText(String.format("Total: $%.2f", order.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderDate, orderItems, orderTotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.text_order_date);
            orderItems = itemView.findViewById(R.id.text_order_items);
            orderTotal = itemView.findViewById(R.id.text_order_total);
        }
    }
}
