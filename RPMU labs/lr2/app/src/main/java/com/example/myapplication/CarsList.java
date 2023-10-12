package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Date;

public class CarsList extends AppCompatActivity {
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.myapplication.databinding.ActivityCarsListBinding binding = ActivityCarsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        updateCarsList(cars);

        Spinner filterSpinner = findViewById(R.id.filterSpinner);
        filterSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.route_list_item, getAllCarBrands()));
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedBrand = filterSpinner.getSelectedItem().toString();
                if (selectedBrand.equals("All")) {
                    updateCarsList(routes);
                } else {
                    ArrayList<Car> filteredList = new ArrayList<>();
                    for (Car car : routes) {
                        if (car.getBrand().equalsIgnoreCase(selectedBrand)) {
                            filteredList.add(car);
                        }
                    }
                    updateCarsList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle the case where nothing is selected
            }
        });
    }

    private String[] getAllCarBrands() {
        HashSet<String> brandSet = new HashSet<>();
        for (Car car : routes) {
            brandSet.add(car.getBrand());
        }
        String[] brands = new String[brandSet.size() + 1];
        brands[0] = "All";
        int index = 1;
        for (String brand : brandSet) {
            brands[index] = brand;
            index++;
        }
        return brands;
    }

        ListView listView = findViewById(R.id.carsListView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, MainActivity.class);
            String selectedCar = listView.getItemAtPosition(i).toString();
            intent.putExtra(MainActivity.SELECTED_ACTIVITY, "Activity 1");
            intent.putExtra(MainActivity.SELECTED_ROUTE, selectedCar);
            startActivity(intent);
        });
    }

    private void updateCarsList(Car[] cars) {
        ListView listView = findViewById(R.id. carsListView);
        ArrayAdapter<Car> adapter = new ArrayAdapter<>(this, R.layout.car_list_item, cars);
        listView.setAdapter(adapter);
    }
}