package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private EditText editTextAmount;
    private Spinner spinnerFromCurrency;
    private Spinner spinnerToCurrency;
    private RadioGroup radioGroup;
    private EditText editTextRate;
    private Button buttonCalculate;
    private TextView textViewResult;

    private String selectedFromCurrency;
    private double savedAmount;
    private double savedResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextAmount = findViewById(R.id.editTextAmount);
        spinnerFromCurrency = findViewById(R.id.spinnerFromCurrency);
        spinnerToCurrency = findViewById(R.id.spinnerToCurrency);
        radioGroup = findViewById(R.id.radioGroup);
        editTextRate = findViewById(R.id.editTextRate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFromCurrency.setAdapter(adapter);
        spinnerToCurrency.setAdapter(adapter);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateConversion();
            }
        });

        if (savedInstanceState != null) {
            selectedFromCurrency = savedInstanceState.getString("selectedFromCurrency", "BYN");
            savedAmount = savedInstanceState.getDouble("savedAmount", 0.0);
            savedResult = savedInstanceState.getDouble("savedResult", 0.0);

            int fromCurrencyPosition = adapter.getPosition(selectedFromCurrency);
            spinnerFromCurrency.setSelection(fromCurrencyPosition);
            spinnerToCurrency.setSelection(adapter.getPosition(selectedFromCurrency));

            editTextAmount.setText(String.valueOf(savedAmount));
            textViewResult.setText(String.format("%.2f %s -> %.2f %s", savedAmount, selectedFromCurrency, savedResult, selectedFromCurrency));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        selectedFromCurrency = spinnerFromCurrency.getSelectedItem().toString();
        outState.putString("selectedFromCurrency", selectedFromCurrency);

        if (!editTextAmount.getText().toString().isEmpty()) {
            savedAmount = Double.parseDouble(editTextAmount.getText().toString());
        }
        outState.putDouble("savedAmount", savedAmount);
        outState.putDouble("savedResult", savedResult);
    }

    private void calculateConversion() {
        selectedFromCurrency = spinnerFromCurrency.getSelectedItem().toString();
        String selectedToCurrency = spinnerToCurrency.getSelectedItem().toString();

        String amountText = editTextAmount.getText().toString();
        String rateText = editTextRate.getText().toString();

        if (amountText.isEmpty() || rateText.isEmpty()) {
            textViewResult.setText("Введите сумму и курс валюты");
            return;
        }

        double amount = Double.parseDouble(amountText);
        double rate = Double.parseDouble(rateText);

        if (amount <= 0 || rate <= 0) {
            textViewResult.setText("Сумма и курс валюты должны быть больше нуля");
            return;
        }

        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        String rateType = selectedRadioButton.getText().toString();
        double result = 0.0;

        if ("Покупка".equals(rateType)) {
            result = amount / rate;
        } else if ("Продажа".equals(rateType)) {
            result = amount * rate;
        }

        savedResult = result;
        textViewResult.setText(String.format("%.2f %s -> %.2f %s", amount, selectedFromCurrency, result, selectedToCurrency));
    }
}