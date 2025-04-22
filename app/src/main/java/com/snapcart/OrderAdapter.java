package com.snapcart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

        StringBuilder items = new StringBuilder();
        for (Product p : order.products) {
            items.append(p.getName()).append(" × ").append(p.getQuantity()).append("\n");
        }

        holder.address.setText("📍 " + order.address);
        holder.payment.setText("💳 " + order.paymentMethod);
        holder.items.setText(items.toString().trim());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView address, payment, items;

        OrderViewHolder(View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.text_order_address);
            payment = itemView.findViewById(R.id.text_order_payment);
            items = itemView.findViewById(R.id.text_order_items);
        }
    }
}
