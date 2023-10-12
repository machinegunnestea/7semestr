package com.example.secondlllrrr;

import androidx.appcompat.app.AppCompatActivity;
import com.example.secondlllrrr.ActivitySelectionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button chooseActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chooseActivityButton = findViewById(R.id.choose_activity_button);
        chooseActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Обработка нажатия на кнопку "Choose Activity"
                openActivitySelection(); // Здесь вызывайте нужный вам метод или переходите к другой активности
            }
        });
    }

    private void openActivitySelection() {
        // Метод для перехода к другой активности или выполнения действий по вашему выбору
        Intent intent = new Intent(MainActivity.this, com.example.secondlllrrr.ActivitySelectionActivity.class);
        startActivity(intent);
    }
}