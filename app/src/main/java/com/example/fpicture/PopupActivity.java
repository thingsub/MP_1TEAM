package com.example.fpicture;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class PopupActivity extends Dialog {

    public PopupActivity(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_layout);

        // 팝업의 크기 조절
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Switch와 Button에 대한 참조 가져오기
        Switch switchBGM = findViewById(R.id.switchBGM);
        Switch switchHGM = findViewById(R.id.switchHGM);
        Button buttonOut = findViewById(R.id.buttonOut);

        // Switch 상태 변경 이벤트 처리
        switchBGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // BGM Switch 상태에 따른 동작 추가
                if (isChecked) {
                    // BGM ON 상태일 때의 동작
                    Toast.makeText(getContext(), "BGM ON", Toast.LENGTH_SHORT).show();
                } else {
                    // BGM OFF 상태일 때의 동작
                    Toast.makeText(getContext(), "BGM OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchHGM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 효과음 Switch 상태에 따른 동작 추가
                if (isChecked) {
                    // 효과음 ON 상태일 때의 동작
                    Toast.makeText(getContext(), "효과음 ON", Toast.LENGTH_SHORT).show();
                } else {
                    // 효과음 OFF 상태일 때의 동작
                    Toast.makeText(getContext(), "효과음 OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Button 클릭 이벤트 처리
        buttonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // "닫기" 버튼 클릭 시의 동작
                dismiss(); // 팝업 닫기
            }
        });
    }
}
