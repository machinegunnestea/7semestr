package com.uni.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.uni.lab3.R;
import com.uni.lab3.model.Product;
import com.uni.lab3.themes.Themes;
import com.uni.lab3.themes.ThemesUtils;

public class AddEditForm extends AppCompatActivity {
    MainActivity.AddEditFormVariants variant;
    AutoCompleteTextView nameAutoComplete;
    AutoCompleteTextView upcAutoComplete;
    AutoCompleteTextView producerAutoComplete;
    AutoCompleteTextView priceAutoComplete;
    AutoCompleteTextView expirationDateAutoComplete;
    AutoCompleteTextView countAutoComplete;

    Product product = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            Themes theme = (Themes) getIntent().getSerializableExtra(MainActivity.CURRENT_THEME);
            setTheme(ThemesUtils.getThemeId(theme));
        }
        setContentView(R.layout.activity_add_edit_form);

        variant = (MainActivity.AddEditFormVariants) getIntent().getSerializableExtra(MainActivity.ADD_EDIT_VARIANT);

        Button addEditButton = findViewById(R.id.addEditButton);
        addEditButton.setText(variant == MainActivity.AddEditFormVariants.ADD ? "add" : "edit");

        nameAutoComplete = findViewById(R.id.nameAutoComplete);
        upcAutoComplete = findViewById(R.id.upcAutoComplete);
        producerAutoComplete = findViewById(R.id.producerAutoComplete);
        priceAutoComplete = findViewById(R.id.priceAutoComplete);
        expirationDateAutoComplete = findViewById(R.id.expirationDateAutoComplete);
        countAutoComplete = findViewById(R.id.countAutoComplete);

        if (getIntent().getSerializableExtra(MainActivity.PRODUCT) != null) {
            product = (Product) getIntent().getSerializableExtra(MainActivity.PRODUCT);
        }

        nameAutoComplete.setText(product.getName());
        upcAutoComplete.setText(String.valueOf(product.getUniversalProductCode()));
        producerAutoComplete.setText(product.getProducer());
        priceAutoComplete.setText(String.valueOf(product.getPrice()));
        expirationDateAutoComplete.setText(product.getExpirationDate());
        countAutoComplete.setText(String.valueOf(product.getCount()));

        addEditButton.setOnClickListener(view -> onSubmit());
    }

    public void onSubmit() {
        product.setName(nameAutoComplete.getText().toString());
        product.setUniversalProductCode(Integer.parseInt(upcAutoComplete.getText().toString()));
        product.setProducer(producerAutoComplete.getText().toString());
        product.setPrice(Integer.parseInt(priceAutoComplete.getText().toString()));
        product.setExpirationDate(expirationDateAutoComplete.getText().toString());
        product.setCount(Integer.parseInt(countAutoComplete.getText().toString()));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(MainActivity.PRODUCT, product);
        final int resultId = variant == MainActivity.AddEditFormVariants.ADD
                ? MainActivity.ADD_PRODUCT_RESULT
                : MainActivity.EDIT_PRODUCT_RESULT;
        this.setResult(resultId, resultIntent);
        this.finish();
    }
}