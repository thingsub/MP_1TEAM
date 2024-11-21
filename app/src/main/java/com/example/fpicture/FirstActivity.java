package com.example.fpicture;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    private TextView textView;
    private StringBuilder selectedLetters;
    private final String[] gridLetters = {"자", "떨", "아", "상", "친", "어", "구", "장", "바", "지", "학", "낭"};
    // 틀린 횟수를 저장할 변수 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // TextView 초기화
        textView = findViewById(R.id.TextView);
        selectedLetters = new StringBuilder();

        // 각 버튼에 대한 클릭 리스너 및 텍스트 설정
        for (int i = 0; i < gridLetters.length; i++) {
            int buttonId = getResources().getIdentifier("button" + (i + 1), "id", getPackageName());
            Button button = findViewById(buttonId);

            final String letter = gridLetters[i];

            // 각 버튼에 해당하는 글자로 텍스트 설정
            button.setText(letter);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 버튼을 누를 때마다 선택된 글자를 TextView에 추가
                    selectedLetters.append(letter);
                    textView.setText(selectedLetters.toString());
                }
            });
        }

        // buttonErase에 대한 클릭 리스너 설정
        Button buttonErase = findViewById(R.id.buttonErase);
        buttonErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buttonErase를 누를 때마다 마지막 글자를 제거
                if (selectedLetters.length() > 0) {
                    selectedLetters.deleteCharAt(selectedLetters.length() - 1);
                    textView.setText(selectedLetters.toString());
                }
            }
        });

        // buttonHint에 대한 클릭 리스너 설정
        Button buttonHint = findViewById(R.id.buttonHint);
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buttonHint를 누를 때 토스트 메시지로 초성 힌트 출력
                showHintToast();
            }
        });

        // buttonPass에 대한 클릭 리스너 설정
        Button buttonPass = findViewById(R.id.buttonPass);
        buttonPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buttonPass를 누를 때 토스트 메시지 표시
                if (textView.getText().length() > 0) {
                    showToast("정답 입력을 눌러주세요.");
                } else {
                    showToast("아쉽네요! 정답은 '자바'입니다.");
                    // If there is no text in textView, go to SecondActivity
                    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                    startActivity(intent);
                }
            }
        });


        // buttonGohome에 대한 클릭 리스너 설정
        Button buttonGohome = findViewById(R.id.buttonGohome);
        buttonGohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // buttonGohome을 누를 때 팝업창 띄우기
                showPopupDialog();
            }
        });

        // buttonEnter에 대한 클릭 리스너 설정
        Button buttonEnter = findViewById(R.id.buttonEnter);
        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (textView.getText().length() == 0) {
                    showToast("정답을 입력해주세요");
                } else {
                }

                // 정답 확인
                checkAnswer();
            }
        });
    }

    private void showPopupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("알림")
                .setMessage("돌아가시겠습니까? \n (게임 기록은 다 지워집니다.)")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(FirstActivity.this, SolveActivity.class);
                        startActivity(intent);
                        dialogInterface.dismiss(); // 다이얼로그 닫기
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // No 버튼을 눌렀을 때의 동작
                        dialogInterface.dismiss(); // 다이얼로그 닫기
                    }
                })
                .create()
                .show();
    }

    private void checkAnswer() {
        // 사용자가 입력한 답을 가져옴
        String userAnswer = selectedLetters.toString().trim();

        // 실제 정답 설정
        String correctAnswer = "자바";

        // 정답 비교
        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            showToast("정답입니다!");
            // 정답이 맞으면 여기에 다음 Activity로 이동하는 코드를 추가
            Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
            startActivity(intent);
        } else {
            showToast("틀렸습니다. 다시 시도하세요.");
        }
    }

    // 토스트 메시지를 표시하는 메서드
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // 힌트
    private void showHintToast() {
        String hintText = "모바일 프로그래밍 수업시간 때 \n 사용하는 프로그래밍 언어";
        Toast.makeText(getApplicationContext(), hintText, Toast.LENGTH_LONG).show();
    }
}
