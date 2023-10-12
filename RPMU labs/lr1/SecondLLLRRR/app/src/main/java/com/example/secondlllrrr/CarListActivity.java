package com.example.secondlllrrr;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        gridView = findViewById(R.id.grid_view);

        List<String> carList = getCarList(); // Здесь необходимо получить список автомобилей заданной марки

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carList);
        gridView.setAdapter(adapter);
    }

    private List<String> getCarList() {
        // Возвращаем список автомобилей заданной марки (данные из модели)
        List<String> carList = new ArrayList<>();
        // Здесь добавьте логику для получения списка автомобилей
        carList.add("BMW");
        carList.add("Mercedes");
        carList.add("Aston Martin");
        carList.add("Reno");
        carList.add("Jeep");
        carList.add("KIA");
        carList.add("Ford");
        // carList.add("Автомобиль 1");
        // carList.add("Автомобиль 2");
        // ...
        return carList;
    }
}