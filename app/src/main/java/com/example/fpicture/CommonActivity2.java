package com.example.fpicture;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class CommonActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_layout2);

        // ImageButton 가져오기
        ImageButton buttonSetting = findViewById(R.id.ButtonSetting);

        // buttonsetting activity 넘기기
        buttonSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupActivity popupActivity = new PopupActivity(CommonActivity2.this);
                popupActivity.show();
            }
        });
    }
}
