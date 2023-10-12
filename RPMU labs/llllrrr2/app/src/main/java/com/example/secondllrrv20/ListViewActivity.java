package com.example.secondllrrv20;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ListView carListView;
    private Spinner brandSpinner;
    private ArrayList<Car> carList;
    private ArrayAdapter<String> brandSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        carListView = findViewById(R.id.car_list_view);
        brandSpinner = findViewById(R.id.brand_spinner);

        // Создаем список автомобилей
        carList = new ArrayList<>();
        // Добавьте данные автомобилей в список

        Car car1 = new Car("BMW", "Model 1", 2022, 2.0);
        Car car2 = new Car("BMW", "Model 2", 2021, 1.8);
        Car car3 = new Car("Mercedes", "Model 1", 2022, 2.0);
        Car car4 = new Car("Mercedes", "Model 2", 2021, 1.8);
        Car car5 = new Car("Aston Martin", "Model 1", 2022, 2.0);
        Car car6 = new Car("Aston Martin", "Model 2", 2021, 1.8);
        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        carList.add(car4);
        carList.add(car5);
        carList.add(car6);

        // Создаем адаптер для ListView
        final CarListAdapter adapter = new CarListAdapter(this, carList);

        // Устанавливаем адаптер для ListView
        carListView.setAdapter(adapter);

        // Создаем адаптер для Spinner
        brandSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getBrands());
        brandSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setAdapter(brandSpinnerAdapter);

        // Устанавливаем слушатель выбора элемента в Spinner
        brandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBrand = brandSpinnerAdapter.getItem(position);

                // Фильтруем список автомобилей по выбранной марке
                ArrayList<Car> filteredList = new ArrayList<>();
                for (Car car : carList) {
                    if (car.getBrand().equals(selectedBrand)) {
                        filteredList.add(car);
                    }
                }

                // Обновляем адаптер ListView с отфильтрованным списком
                adapter.updateList(filteredList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ничего не делаем
            }
        });

        // Устанавливаем слушатель щелчков на элементы ListView
        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car selectedCar = carList.get(position);
                String selectedData = selectedCar.getModel() + ", " + selectedCar.getYear() + ", " + selectedCar.getEngine();

                // Возвращаем выбранные данные на главную активность
                Intent intent = new Intent();
                intent.putExtra("selectedData", selectedData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private ArrayList<String> getBrands() {
        ArrayList<String> brands = new ArrayList<>();
        // Добавьте марки автомобилей в список brands
        return brands;
    }
}