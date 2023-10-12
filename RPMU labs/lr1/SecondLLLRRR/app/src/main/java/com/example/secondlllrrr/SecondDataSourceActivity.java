package com.example.secondlllrrr;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondDataSourceActivity extends AppCompatActivity {

    private Spinner spinner;
    private ListView listView;
    private TextView selectedDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_data_source);

        spinner = findViewById(R.id.spinner);
        listView = findViewById(R.id.list_view);
        selectedDataTextView = findViewById(R.id.selected_data_text_view);

        List<String> spinnerData = getSpinnerData(); // Здесь необходимо получить данные для выпадающего списка
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedData = parent.getItemAtPosition(position).toString();
                selectedDataTextView.setText(selectedData);
                updateListView(selectedData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateListView(String selectedData) {
        List<String> dataList = getDataList(selectedData); // Здесь необходимо получить данные для ListView в соответствии с выбраннымзначением в Spinner

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
    }

    private List<String> getSpinnerData() {
        // Возвращаем данные для выпадающего списка
        List<String> spinnerData = new ArrayList<>();
        // Добавьте логику для получения данных
        spinnerData.add("BMW");
        spinnerData.add("Mercedes");
        spinnerData.add("Aston Martin");
        spinnerData.add("Reno");
        spinnerData.add("Jeep");
        spinnerData.add("KIA");
        spinnerData.add("Ford");

        // spinnerData.add("Данные 1");
        // spinnerData.add("Данные 2");
        // ...
        return spinnerData;
    }

    private List<String> getDataList(String selectedData) {
        // Возвращаем данные для ListView в зависимости от выбранного значения в Spinner
        List<String> dataList = new ArrayList<>();
        // Добавьте логику для получения соответствующих данных
        dataList.add("Данные 1 для " + selectedData);
        dataList.add("Данные 2 для " + selectedData);
        dataList.add("Данные 3 для " + selectedData);
        // ...
        return dataList;
    }
}