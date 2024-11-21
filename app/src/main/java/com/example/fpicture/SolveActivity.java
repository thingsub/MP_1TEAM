package com.example.fpicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SolveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button startButton = findViewById(R.id.buttonSQStart);
        Button quitButton = findViewById(R.id.buttonSQQuit);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MainActivity로 이동하는 Intent 생성
                Intent intent = new Intent(SolveActivity.this, FirstActivity.class);

                // 액티비티 시작
                startActivity(intent);
            }
        });

    }
}