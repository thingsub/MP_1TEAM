package com.example.makequiz1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CanvasView extends View {

    private Paint pencilPaint;
    private Paint eraserPaint;
    private Path path;  // 사용자의 그림을 담는 Path 객체
    private ArrayList<Path> paths;  // 모든 그림 경로를 저장하는 리스트
    private ArrayList<Path> undonePaths;  // 취소된 그림 경로를 저장하는 리스트
    private boolean isPencil = true;  // 현재 모드가 연필 모드인지 여부

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaints();  // Paint 객체 초기화 메서드 호출
    }

    // Paint 객체 초기화 메서드
    private void setupPaints() {
        // 연필 그리기용 Paint 객체 설정
        pencilPaint = new Paint();
        pencilPaint.setColor(Color.BLACK);
        pencilPaint.setStyle(Paint.Style.STROKE);
        pencilPaint.setStrokeWidth(5);

        // 사용자의 그림을 담을 Path 객체 및 리스트 초기화
        path = new Path();
        paths = new ArrayList<>();
        undonePaths = new ArrayList<>();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        // 저장된 모든 그림 경로를 canvas에 그림
        for (Path p : paths) {
            canvas.drawPath(p, isPencil ? pencilPaint : eraserPaint);
        }
        // 현재 그림 중인 경로도 그림
        canvas.drawPath(path, isPencil ? pencilPaint : eraserPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);  // 터치 시작 시 호출
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);  // 터치 중인 동안 호출
                break;
            case MotionEvent.ACTION_UP:
                touchUp();  // 터치 종료 시 호출
                break;
        }
        invalidate();  // 화면을 다시 그리도록 강제 호출
        return true;
    }

    // 터치 시작 시 호출되는 메서드
    private void touchStart(float x, float y) {
        path.reset();
        path.moveTo(x, y);
    }

    // 터치 중인 동안 호출되는 메서드
    private void touchMove(float x, float y) {
        path.lineTo(x, y);
    }

    // 터치 종료 시 호출되는 메서드
    private void touchUp() {
        paths.add(new Path(path));
        path.reset();
    }

    // 마지막 그림 작업을 되돌리는 메서드
    public void undo() {
        if (paths.size() > 0) {
            undonePaths.add(paths.remove(paths.size() - 1));
            invalidate();
        }
    }
    // 캔버스를 초기화하여 모든 그림을 지우는 메서드
    public void clearCanvas() {
        paths.clear();
        undonePaths.clear();
        invalidate();
    }

    public Bitmap createDrawingBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 그림 그리기
        for (Path p : paths) {
            canvas.drawPath(p, isPencil ? pencilPaint : eraserPaint);
        }

        return bitmap;
    }
}