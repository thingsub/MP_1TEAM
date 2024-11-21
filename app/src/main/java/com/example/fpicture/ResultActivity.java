package com.example.fpicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // 결과 이미지를 표시하는 ImageView
        ImageView resultImageView = findViewById(R.id.imageView);
        // resultImageView.setImageResource(R.drawable.이미지_이름);

        // 다시 시작하기 버튼
        Button restartButton = findViewById(R.id.button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, SolveActivity.class);
                startActivity(intent);
            }
        });
    }
}


