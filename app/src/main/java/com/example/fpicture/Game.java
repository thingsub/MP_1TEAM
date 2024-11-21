package com.example.fpicture;


import static java.lang.Thread.sleep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


//공용 클래스 Game의 뷰 확장
public class Game extends View {

    //스크린 폭, 높이 정보를 저장할 정수형 변수
    int scrw, scrh;
    //캐릭터 이미지 기준점의 x좌표와 y좌표 위치 값을 저장할 실수형 변수
    int xd, yd;
    int count = 0;


    boolean start = false;
    private String DirButton;
    private String DirButton2;

    int n;
    Paint p = new Paint();
    private GameThread T;


    //Game이란 이름의 생성자 생성 -> Context는 앱에 대한 다양한 정보가 들어 있다. AttributeSet은 xml정보를 가져온다.
    public Game(Context con, AttributeSet at) {
        //부모 클래스의 생성자를 불러와서 초기화시킨다.
        super(con, at);
    }


    @Override
    //뷰의 크기가 변경될 때 호출
    protected void onSizeChanged(int sw, int sh, int esw, int esh) {
        //부모 클래스의 멤버 변수를 참조한다.
        super.onSizeChanged(sw, sh, esw, esh);
        //뷰의 폭 정보 저장
        this.scrw = sw;
        //뷰의 높이 정보 저장
        this.scrh = sh;


        //쓰레드 값이 비었다면
        if (T == null) {
            //GameThread()함수를 돌린 값을 넣어줌
            T = new GameThread();
            //쓰레드 시작
            T.start();
        }
    }

    // 뷰가 윈도우에서 분리될 때마다 발생.
    @Override
    protected void onDetachedFromWindow() {
        //쓰레드의 run 값으로 false 값을 줌.
        T.run = false;
        //부모 클래스의 멤버 변수를 참조
        super.onDetachedFromWindow();
    }

    @Override
    //캔버스 위에 그리기
    protected void onDraw(Canvas canvas) {

        // Define grid cell width and height
        int gridCellWidth = scrw / 8;
        int gridCellHeight = scrh / 4;

        // Draw vertical grid lines
        for (int i = 0; i <= 8; i++) {
            canvas.drawLine(i * gridCellWidth, 0, i * gridCellWidth, scrh, p);
        }

        // Draw horizontal grid lines
        for (int i = 0; i <= 4; i++) {
            canvas.drawLine(0, i * gridCellHeight, scrw, i * gridCellHeight, p);
        }

        p.setColor(Color.BLACK);
        p.setTextSize(scrh / 16);
        Bitmap[] main = new Bitmap[12];


        for(int i=0; i<12; i++) {
            //캐릭터 그림 파일의 경로를 배열에 저장해줌.
            main[i] = BitmapFactory.decodeResource(getResources(), R.drawable.character01 + i);

            //캐릭터 그림 파일의 크기를 설정해줌. 폭은 (scrw-scrw를 64로 나눈 값의 나머지) 나누기 8, 높이는 (scrh-scrh을 32로 나눈 값의 나머지) 나누기 4
            main[i] = Bitmap.createScaledBitmap(main[i], (scrw-scrw%64) / 8, (scrh-scrh%32) / 4, true);

            //i와 n의 값이 같다면
            if(i==n) {
                // 캐릭터 그림파일을 scrw/2+xd, scrh/2+yd 지점을 기준으로 그려줌.
                canvas.drawBitmap(main[i], (float) scrw / 2 + xd , (float) scrh  / 2 + yd, null);
            }
        }
        if ((scrw / 2 + xd) <= 0) {
            // Character has reached the left end, start another activity or perform an action
            Intent intent = new Intent(getContext(), SolveActivity.class);
            getContext().startActivity(intent);
        }
        int characterRightEdge = scrw / 2 + xd + (scrw - scrw % 64) / 8; // Right edge of the character

        // Check if the character has reached the right end of the screen
        if (characterRightEdge >= scrw) {
            // Character has reached the right end, start another activity or perform an action
            Intent intent = new Intent(getContext(), DrawMainActivity.class);
            getContext().startActivity(intent);
        }




        Bitmap IS[][] = new Bitmap[1][4];
        Bitmap I = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.dir);
        I = Bitmap.createScaledBitmap(I, scrw / 8, scrh, true);
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                IS[i][j] = Bitmap.createBitmap(I, i * scrw / 8, j * scrh / 4, scrw / 8, scrh / 4);
            }
        }

        canvas.drawBitmap(IS[0][0], scrw / 8, scrh - scrh / 2, null);
        canvas.drawBitmap(IS[0][1], 0, scrh - scrh / 4, null);
        canvas.drawBitmap(IS[0][2], scrw / 4, scrh - scrh / 4, null);
        canvas.drawBitmap(IS[0][3], scrw / 8, scrh - scrh / 4, null);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //만약 회면을 터치했다면
        if(event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            //오른쪽 버튼을 클릭했다면
            if((int) event.getX()>scrw / 4 && (int) event.getX()<scrw*3 / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
                if(start==false && count==0) {
                    //버튼을 클릭했음을 알림
                    start=true;
                    //오른쪽 버튼을 클릭헸음을 알림
                    DirButton="Right";
                }
                //오른쪽 버튼을 클릭했음을 알림
                DirButton2="Right";
            }
            //왼쪽 버튼을 클릭했다면
            else if((int) event.getX()>0 && (int) event.getX()<scrw / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
                if(start==false && count==0) {
                    //버튼을 클릭했음을 알림
                    start=true;
                    //왼쪽 버튼을 클릭헸음을 알림
                    DirButton="Left";
                }
                //왼쪽 버튼을 클릭헸음을 알림
                DirButton2="Left";
            }
            //위쪽 버튼을 클릭했다면
            else if((int) event.getX()>scrw / 8 && (int) event.getX()<scrw / 4 && (int) event.getY() < scrh - scrh / 4 && (int) event.getY() > scrh - scrh / 2) {
                //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
                if(start==false && count==0) {
                    //버튼을 클릭했음을 알림
                    start=true;
                    //위쪽 버튼을 클릭헸음을 알림
                    DirButton="Up";
                }
                //위쪽 버튼을 클릭헸음을 알림
                DirButton2="Up";
            }
            //아래 버튼을 클릭했다면
            else if((int) event.getX()>scrw / 8 && (int) event.getX()<scrw / 4 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //버튼을 클릭한 상태가 아니고, 캐릭터가 이동중이 아니라면
                if(start==false && count==0) {
                    //버튼을 클릭했음을 알림
                    start=true;
                    //아래 버튼을 클릭헸음을 알림
                    DirButton="Down";
                }
                //아래 버튼을 클릭헸음을 알림
                DirButton2="Down";
            }


            //방향키 버튼을 클릭하고 있는 것이 아니라면
            else {
                //버튼을 클릭하고 있는 상태가 아니라고 선언함
                start = false;
            }
        }

        //화면에서 손을 땠을때
        if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
            //오른쪽 버튼 위에서 손을 땠다면
            if((int) event.getX()>scrw / 4 && (int) event.getX()<scrw*3 / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
                start = false;
            }
            //왼쪽 버튼 위에서 손을 땠다면
            else if((int) event.getX()>0 && (int) event.getX()<scrw / 8 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
                start = false;
            }
            //위쪽 버튼 위에서 손을 땠다면
            else if((int) event.getX()>scrw / 8 && (int) event.getX()<scrw / 4 && (int) event.getY() < scrh - scrh / 4 && (int) event.getY() > scrh - scrh / 2) {
                //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
                start = false;
            }
            //아래쪽 버튼 위에서 손을 땠다면
            else if((int) event.getX()>scrw / 8 && (int) event.getX()<scrw / 4 && (int) event.getY() < scrh && (int) event.getY() > scrh - scrh / 4) {
                //방향키 버튼을 클릭하고 있는 상태가 아님을  선언
                start = false;
            }
        }

        //return 한다.
        return true;
    }

    class GameThread extends Thread {
        //run은 0 또는 1의 값을 가질 수 있으며, true 값을 넣어줌. (true = 1, false = 0)
        public boolean run = true;

        int gridCellWidth;
        int gridCellHeight;
        int characterGridX;
        int characterGridY;

        public GameThread() {
            gridCellWidth = (scrw - scrw % 64) / 64; // Adjust as needed
            gridCellHeight = (scrh - scrh % 32) / 32; // Adjust as needed
            characterGridX = 2; // Initial X position in grid cells
            characterGridY = 2; // Initial Y position in grid cells
        }


        public void run() {
            while (run) {
                try {
                    postInvalidate();
                    if (count == 20) {
                        count = 0;
                        DirButton = DirButton2;
                    }
                    if (start == true && count == 0) {
                        count += 1;
                        if (DirButton.equals("Down")) {
                            characterGridY += 1; // Move character one cell down
                            yd += gridCellHeight; // Update yd for drawing
                        } else if (DirButton.equals("Up")) {
                            characterGridY -= 1; // Move character one cell up
                            yd -= gridCellHeight; // Update yd for drawing
                        } else if (DirButton.equals("Left")) {
                            characterGridX -= 1; // Move character one cell left
                            xd -= gridCellWidth; // Update xd for drawing
                        } else if (DirButton.equals("Right")) {
                            characterGridX += 1; // Move character one cell right
                            xd += gridCellWidth; // Update xd for drawing
                        }
                    }
                    if (count > 0 && count < 20) {
                        count += 1;
                    }
                    if (start == true && DirButton == "Down" && count != 20 || start == false && count > 0 && count < 20 && DirButton == "Down") {
                        if (count % 4 == 0) {
                            yd += scrh / 80;
                            n = 0;
                        } else if (count % 4 == 1 || count % 4 == 3) {
                            yd += scrh / 80;
                            n = 1;
                        } else if (count % 4 == 2) {
                            yd += scrh / 80;
                            n = 2;
                        }
                    }
                    if (start == true && DirButton == "Up" && count != 20 || start == false && count > 0 && count < 20 && DirButton == "Up") {
                        if (count % 4 == 0) {
                            yd -= scrh / 80;
                            n = 6;
                        } else if (count % 4 == 1 || count % 4 == 3) {
                            yd -= scrh / 80;
                            n = 7;
                        } else if (count % 4 == 2) {
                            yd -= scrh / 80;
                            n = 8;
                        }
                    }
                    if (start == true && DirButton == "Left" && count != 20 || start == false && count > 0 && count < 20 && DirButton == "Left") {
                        if (count % 4 == 0) {
                            xd -= scrw / 160;
                            n = 3;
                        } else if (count % 4 == 1 || count % 4 == 3) {
                            xd -= scrw / 160;
                            n = 4;
                        } else if (count % 4 == 2) {
                            xd -= scrw / 160;
                            n = 5;
                        }
                    }
                    if (start == true && DirButton == "Right" && count != 20 || start == false && count > 0 && count < 20 && DirButton == "Right") {
                        if (count % 4 == 0) {
                            xd += scrw / 160;
                            n = 9;
                        } else if (count % 4 == 1 || count % 4 == 3) {
                            xd += scrw / 160;
                            n = 10;
                        } else if (count % 4 == 2) {
                            xd += scrw / 160;
                            n = 11;
                        }
                    }
                    sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
