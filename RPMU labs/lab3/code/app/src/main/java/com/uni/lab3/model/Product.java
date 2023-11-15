package com.uni.lab3.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("universalProductCode")
    private int universalProductCode;
    @SerializedName("producer")
    private String producer;
    @SerializedName("price")
    private int price;
    @SerializedName("expirationDate")
    private String expirationDate;
    @SerializedName("count")
    private int count;

    public Product() {
        this.name = "Sample name";
        this.universalProductCode = 0;
        this.producer = "Sample producer";
        this.price = 0;
        this.expirationDate = "01.01.2000";
        this.count = 0;
    }

    public Product(String name, int universalProductCode, String producer, int price, String expirationDate, int count) {
        this.name = name;
        this.universalProductCode = universalProductCode;
        this.producer = producer;
        this.price = price;
        this.expirationDate = expirationDate;
        this.count = count;
    }

    @NonNull
    public Product clone() {
        Product product = new Product(
                getName(),
                getUniversalProductCode(),
                getProducer(),
                getPrice(),
                getExpirationDate(),
                getCount()
        );
        product.setId(getId());
        return product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUniversalProductCode() {
        return universalProductCode;
    }

    public void setUniversalProductCode(int universalProductCode) {
        this.universalProductCode = universalProductCode;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toShortString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", universalProductCode=" + universalProductCode +
                ", producer='" + producer + '\'' +
                ", price=" + price +
                ", expirationDate='" + expirationDate + '\'' +
                ", count=" + count +
                '}';
    }
}
