package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class CarsGrid extends AppCompatActivity {
    private final Car[] cars = new Car[] {
            new Car("Aston Martin", "Model 1", 2022, 2.0),
            new Car("BMW", "Model 11", 2022, 2.0),
            new Car("Mercedes", "Model 12", 2022, 2.0),
            new Car("Mercedes", "Model 13", 2022, 2.0),
            new Car("Reno", "Model 14", 2022, 2.0),
            new Car("Reno", "Model 15", 2022, 2.0),
            new Car("Aston Martin", "Model 16", 2022, 2.0),
            new Car("BMW", "Model 17", 2022, 2.0)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars_grid);

        updateCarsGrid(cars);
        GridView gridView = findViewById(R.id.carsGridView);
        gridView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, MainActivity.class);
            String selectedCar = gridView.getItemAtPosition(i).toString();
            intent.putExtra(MainActivity.SELECTED_ACTIVITY, "Activity 2");
            intent.putExtra(MainActivity.SELECTED_ROUTE, selectedCar);
            startActivity(intent);
        });
    }

    private void updateCarsGrid(Car[] cars) {
        GridView gridView = findViewById(R.id.carsGridView);
        ArrayAdapter<Car> adapter = new ArrayAdapter<>(this, R.layout.car_grid_item, cars);
        gridView.setAdapter(adapter);
    }
}