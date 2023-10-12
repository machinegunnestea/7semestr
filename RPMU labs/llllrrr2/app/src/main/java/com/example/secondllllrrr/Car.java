package com.example.secondllllrrr;
public class Car {
    private String brand;
    private String model;
    private int year;
    private double engine;

    public Car(String brand, String model, int year, double engine) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.engine = engine;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getEngine() {
        return engine;
    }
}