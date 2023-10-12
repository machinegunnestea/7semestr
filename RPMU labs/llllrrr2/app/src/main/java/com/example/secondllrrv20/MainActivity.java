package com.example.secondllrrv20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView selectedDataTextView;
    private RadioGroup dataSourceRadioGroup;
    private Button selectButton;

    private static final int GRID_VIEW_ACTIVITY_REQUEST_CODE = 1;
    private static final int LIST_VIEW_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDataTextView = findViewById(R.id.selected_data_text_view);
        dataSourceRadioGroup = findViewById(R.id.data_source_radio_group);
        selectButton = findViewById(R.id.select_button);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = dataSourceRadioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId == R.id.grid_view_radio_button) {
                    // Запускаем активность GridView
                    Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                    startActivityForResult(intent, GRID_VIEW_ACTIVITY_REQUEST_CODE);
                } else if (selectedRadioButtonId == R.id.list_view_radio_button) {
                    // Запускаем активность ListView
                    Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
                    startActivityForResult(intent, LIST_VIEW_ACTIVITY_REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GRID_VIEW_ACTIVITY_REQUEST_CODE || requestCode == LIST_VIEW_ACTIVITY_REQUEST_CODE) {
                String selectedData = data.getStringExtra("selectedData");
                selectedDataTextView.setText(selectedData);
            }
        }
    }
}