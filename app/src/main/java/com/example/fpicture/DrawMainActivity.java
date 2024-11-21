package com.example.fpicture;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;
import android.content.DialogInterface;

import com.example.fpicture.MainActivity;
import com.example.fpicture.R;

import java.io.ByteArrayOutputStream;


public class DrawMainActivity extends AppCompatActivity {

    private com.example.makequiz1.CanvasView canvasView;
    private EditText answerEditText;
    private com.example.makequiz1.DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawmain);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        canvasView = findViewById(R.id.canvasView);
        answerEditText = findViewById(R.id.answerEditText);
        dbHelper = new com.example.makequiz1.DatabaseHelper(this);

        Button eraserButton = findViewById(R.id.eraserButton);
        Button undoButton = findViewById(R.id.undoButton);
        Button submitBtn = findViewById(R.id.submitBtn);
        Button homeReturnBtn = findViewById(R.id.homeReturnBtn);

        homeReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DrawMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        eraserButton.setOnClickListener(new View.OnClickListener() {// 전체 지우기 모드
            @Override
            public void onClick(View v) {
                showClearConfirmationDialog();
            }
        });

        undoButton.setOnClickListener(new View.OnClickListener() { // 획 되돌리기 모드
            @Override
            public void onClick(View v) {
                canvasView.undo();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() { // 제출하기
            @Override
            public void onClick(View v) {
                saveDrawingAndAnswer();
                showToast("제출 완료!", Toast.LENGTH_SHORT);

                // 제출 완료시 캔버스와 정답 텍스트 비우기
                canvasView.clearCanvas();
                answerEditText.getText().clear();
            }
        });
    }

    private void showToast(String message, int duration) {
        Toast.makeText(getApplicationContext(), message, duration).show();
    }

    private void saveDrawingAndAnswer() {
        // 그림을 Bitmap으로 변환
        Bitmap drawingBitmap = canvasView.createDrawingBitmap();

        // Bitmap을 byte 배열로 변환
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        drawingBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // 답변 텍스트
        String answerText = answerEditText.getText().toString();

        // 데이터베이스에 저장
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("drawing_data", imageBytes);
        values.put("answer", answerText);

        long newRowId = db.insert("drawing_table", null, values);

        db.close();
    }


    private void showClearConfirmationDialog() { AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("전체 지우기 하시겠습니까?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'YES' 버튼을 눌렀을 때의 동작
                        canvasView.clearCanvas();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 'NO' 버튼을 눌렀을 때의 동작
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}