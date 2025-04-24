package com.snapcart.data.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = "orders")
@TypeConverters(DateConverter.class)
public class OrderEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String itemsSummary;
    public double totalAmount;
    public String address;
    public String paymentMethod;
    public Date date;

    public OrderEntity() {} // default constructor

    @Ignore
    public OrderEntity(String itemsSummary, double totalAmount, String address, String paymentMethod, Date date) {
        this.itemsSummary = itemsSummary;
        this.totalAmount = totalAmount;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.date = date;
    }
}
