package com.example.fpicture;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.TextView;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        TextView textView1 = findViewById(R.id.textView);
        textView1.setText("Loading... please wait");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 애니메이션 효과 끝나고 MainActivity2로 전환
                startActivity(new Intent(IntroActivity.this, RotateActivity.class));
                // 현재 액티비티 종료하지 않음
            }
        }, 4500);
    }
}
