package com.uni.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.uni.lab3.R;
import com.uni.lab3.themes.Themes;
import com.uni.lab3.themes.ThemesUtils;

public class ConstrainedSearch extends AppCompatActivity {
    private String currentName = "";
    private String lastMaxPriceString = "";
    private int currentMaxPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            Themes theme = (Themes) getIntent().getSerializableExtra(MainActivity.CURRENT_THEME);
            setTheme(ThemesUtils.getThemeId(theme));
        }
        setContentView(R.layout.activity_constrained_search);

        EditText nameInput = findViewById(R.id.nameInput);
        EditText maxPriceInput = findViewById(R.id.maxPriceInput);
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentName = charSequence.toString();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        maxPriceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isInteger(charSequence.toString())) {
                    lastMaxPriceString = charSequence.toString();
                    currentMaxPrice = Integer.parseInt(charSequence.toString());
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isInteger(charSequence.toString()) && charSequence.length() > 0) {
                    int previousSelection = maxPriceInput.getSelectionEnd() - 1;
                    maxPriceInput.setText(lastMaxPriceString);
                    maxPriceInput.setSelection(Math.min(previousSelection, lastMaxPriceString.length()));
                } else {
                    lastMaxPriceString = charSequence.toString();
                    currentMaxPrice = Integer.parseInt(charSequence.toString());
                }
                if (charSequence.length() == 0) {
                    lastMaxPriceString = "";
                    currentMaxPrice = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        Button constrainedSearchButton = findViewById(R.id.constrainedSearchButton);
        constrainedSearchButton.setOnClickListener(view -> {
            this.setResult(MainActivity.CONSTRAINED_SEARCH_RESULT, getResultIntent());
            this.finish();
        });
    }

    public Intent getResultIntent() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name", currentName);
        resultIntent.putExtra("maxPrice", currentMaxPrice);
        return resultIntent;
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Integer.parseInt(strNum);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}