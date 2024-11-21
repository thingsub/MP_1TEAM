package com.example.fpicture;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

public class RotateActivity extends AppCompatActivity {

    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate);

        imageView1 = findViewById(R.id.haha);

        TextView textView1 = findViewById(R.id.textView);
        textView1.setText("로딩 중... 잠시만 기다려 주세요");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        // 애니메이션 리소스 파일 로드
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);

        // 애니메이션을 첫 번째 이미지뷰에 설정
        imageView1.setAnimation(animation1);

        // 애니메이션 리스너 등록
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // 애니메이션 시작 시 동작
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // 애니메이션이 종료될 때의 동작
                startActivity(new Intent(RotateActivity.this, IntroActivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // 애니메이션 반복 시 동작
            }
        });
    }
}
