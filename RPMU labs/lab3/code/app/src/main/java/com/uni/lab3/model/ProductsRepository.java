package com.uni.lab3.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProductsRepository implements Serializable {
    private List<Product> products;

    public ProductsRepository(Product[] products) {
        this.products = new LinkedList<>(Arrays.asList(products));
    }

    public Product[] getAll() {
        return products.stream().toArray(Product[]::new);
    }

    public Product getByIndex(int index) {
        return products.get(index).clone();
    }

    public Product getById(int id) {
        return products.stream().filter(product -> product.getId() == id).findFirst().get().clone();
    }

    public void add(Product product) {
        product.setId(getNextId());
        addByIndex(length(), product);
    }

    public void addByIndex(int index, Product product) {
        products.add(index, product);
    }

    public void update(Product product) {
        int index = IntStream.range(0, products.size())
                .filter(i -> product.getId() == products.get(i).getId())
                .findFirst().orElse(-1);
        remove(product);
        products.add(index, product);
    }

    public void remove(@NonNull Product product) {
        removeById(product.getId());
    }

    public void removeById(int id) {
        products = products.stream().filter(product -> product.getId() != id).collect(Collectors.toList());
    }

    public int getNextId() {
        return Math.max(0, products.stream().map(Product::getId).mapToInt(i -> i).max().orElse(0)) + 1;
    }

    public int length() {
        return products.size();
    }
}
