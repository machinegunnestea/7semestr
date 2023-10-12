package com.example.secondlllrrr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ActivitySelectionActivity extends AppCompatActivity {

    private Button carListButton;
    private Button secondDataSourceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_selection);

        carListButton = findViewById(R.id.car_list_button);
        secondDataSourceButton = findViewById(R.id.second_data_source_button);

        carListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCarListActivity();
            }
        });

        secondDataSourceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondDataSourceActivity();
            }
        });
    }

    private void openCarListActivity() {
        Intent intent = new Intent(ActivitySelectionActivity.this, CarListActivity.class);
        startActivity(intent);
    }

    private void openSecondDataSourceActivity() {
        Intent intent = new Intent(ActivitySelectionActivity.this, SecondDataSourceActivity.class);
        startActivity(intent);
    }
}