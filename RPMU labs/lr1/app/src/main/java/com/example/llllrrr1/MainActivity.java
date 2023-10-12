package com.example.llllrrr1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    private EditText surnameEditText;
    private Button generateButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surnameEditText = findViewById(R.id.surnameEditText);
        generateButton = findViewById(R.id.generateButton);
        resultTextView = findViewById(R.id.resultTextView);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String surname = surnameEditText.getText().toString();
                int randomGrade = generateRandomGrade();
                String result = surname + ": " + randomGrade;

                resultTextView.setText(result);
            }
        });
    }

    private int generateRandomGrade() {
        Random random = new Random();
        return random.nextInt(10) + 1; // Генерация случайного числа от 1 до 10
    }
}