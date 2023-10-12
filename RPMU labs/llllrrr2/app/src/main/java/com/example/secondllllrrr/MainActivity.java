package com.example.secondllllrrr;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.code.databinding.ActivityMainBinding;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private String selectedActivity;
    public static final String SELECTED_ACTIVITY = "SELECTED_ACTIVITY";
    public static final String SELECTED_ROUTE = "SELECTED_ROUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.secondllllrrr.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RadioGroup activitySelectorRadioGroup = findViewById(R.id.activitySelector);
        if (getIntent().getStringExtra(SELECTED_ACTIVITY) != null) {
            String selectedActivity = getIntent().getStringExtra(SELECTED_ACTIVITY);
            if (selectedActivity.equals("Activity 2")) {
                ((RadioButton) findViewById(R.id.radioButtonActivity2)).setChecked(true);
            }
        }
        selectedActivity = ((RadioButton) findViewById(activitySelectorRadioGroup.getCheckedRadioButtonId())).getText().toString();

        activitySelectorRadioGroup.setOnCheckedChangeListener(this::onSelectActivity);
        updateSelectedActivityTextView();

        findViewById(R.id.openRoutesButton).setOnClickListener(view -> onOpenRoutes());

        TextView selectedRouteTextView = findViewById(R.id.selectedRouteTextView);
        selectedRouteTextView.setText(getIntent().getStringExtra(SELECTED_ROUTE));
    }

    private void onSelectActivity(RadioGroup radioGroup, int i) {
        selectedActivity = ((RadioButton) radioGroup.findViewById(i))
                .getText().toString();
        updateSelectedActivityTextView();
    }

    private String getSelectedActivityText() {
        return  "Selected activity: " + selectedActivity;
    }

    public void onOpenRoutes() {
        Intent intent = new Intent(this,
                selectedActivity.equals("Activity 1") ? RoutesList.class : RoutesGrid.class);
        startActivity(intent);
    }

    private void updateSelectedActivityTextView() {
        TextView textView = findViewById(R.id.selectedActivityTextView);
        textView.setText(getSelectedActivityText());
        ((TextView) findViewById(R.id.selectedRouteTextView)).setText("");
    }
}