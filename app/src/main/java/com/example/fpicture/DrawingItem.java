package com.example.makequiz1;
public class DrawingItem {
    private String drawingData;
    private String answer;

    public DrawingItem(String drawingData, String answer) {
        this.drawingData = drawingData;
        this.answer = answer;
    }

    public String getDrawingData() {
        return drawingData;
    }

    public String getAnswer() {
        return answer;
    }
}