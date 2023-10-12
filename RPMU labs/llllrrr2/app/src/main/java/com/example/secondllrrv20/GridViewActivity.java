package com.example.secondllrrv20;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private GridView carGridView;
    private ArrayList<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        carGridView = findViewById(R.id.car_grid_view);

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

        // Создаем адаптер для GridView
        final CarGridAdapter adapter = new CarGridAdapter(this, carList);

        // Устанавливаем адаптер для GridView
        carGridView.setAdapter(adapter);

        // Устанавливаем слушатель щелчков на элементы GridView
        carGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
}