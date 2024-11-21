package com.example.fpicture;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class FinalActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    Button btn;

    String[] messages = {
            "영원히 살 것처럼 꿈꾸고" + "\n" + "오늘 죽을 것처럼 살아라." + "\n" + "오늘이 내 인생의 마지막 날이라면 여러분은 무엇을 하실 건가요? " +
                    "하고 싶은 게 있다면 더이상 미루지 말고 지금 도전해보자고요 :)",
            "우리가 꿈꿀 수 있다면 이룰 수도 있습니다. " +
                    "꿈을 가지고 희망을 가져요. " +
                    "그리고 그 꿈을 멋지게 실현시킬 " +
                    "나의 미래를 생각하며 오늘 하루도 힘내봅시다 화이팅! :)",
            "미래를 계획하는 일도 필요하지만" +
                    " 가장 중요한 것은 오늘을 후회없이 사는 겁니다.\n" +
                    "\n" +
                    "무의미한 삶을 사는 것 같아도 항상 \n " +
                    "최선을 다하는 지금의 나를 응원해봅니다. :)",
            "긴 인생은 좋지 않을 수 있지만" +
                    "\n" +
                    "우리의 좋은 인생은 충분히 길어요" +
                    "\n" +
                    "과거에 대한 미련과 후회는 접어두고" +
                    "\n" +
                    "앞으로 남은 인생을 어떻게 행복하게 살아갈지 고민해보아요." +
                    "\n" +
                    "후회만 하고 살기엔 너무나 아까운 우리의 인생이니까요. :)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_dialog);
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        // 무작위 메시지 출력 버튼 리스너
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 무작위로 메시지 선택
                Random random = new Random();
                String randomMessage = messages[random.nextInt(messages.length)];

                // 팝업창의 디자인을 적용한 레이아웃을 inflate
                View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

                // 레이아웃 내부의 요소들을 가져옴
                TextView titleTextView = dialogView.findViewById(R.id.dialog_title);
                TextView messageTextView = dialogView.findViewById(R.id.dialog_message);

                // 요소들의 내용을 설정
                titleTextView.setText("즐거우셨나요?");
                messageTextView.setText(randomMessage);

                AlertDialog.Builder ad = new AlertDialog.Builder(FinalActivity.this);
                ad.setView(dialogView);

                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = ad.create();

                // 팝업창의 둥근 모서리를 위해 Window의 배경을 투명하게 설정
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                alertDialog.show();
            }
        });

        // 종료 버튼 리스너
        Button closeBtn = findViewById(R.id.close_button);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // BGM 시작
        startBGM();
    }

    private void startBGM() {
        mediaPlayer = MediaPlayer.create(this, R.raw.hiroshi); // 여기서 hiroshi는 raw 폴더에 있는 BGM 파일명
        mediaPlayer.setLooping(true);

        // 볼륨 설정 (0.0 ~ 1.0, 1.0이 최대)
        float volume = 1.0f;
        mediaPlayer.setVolume(volume, volume);

        mediaPlayer.start();
    }

    private void stopBGM() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        // 액티비티가 파괴될 때 BGM 중지
        stopBGM();
        super.onDestroy();
    }
}
