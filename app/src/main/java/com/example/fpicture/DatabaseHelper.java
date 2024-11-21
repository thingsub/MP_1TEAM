package com.example.makequiz1;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_PATH = "/mnt/sdcard/"; // 원하는 경로로 변경
    private static final String DATABASE_NAME = "submitquiz.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 생성 시 호출되는 메서드
        String createTableQuery = "CREATE TABLE drawing_table (_id INTEGER PRIMARY KEY AUTOINCREMENT, drawing_data BLOB, answer TEXT)";
        db.execSQL(createTableQuery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // You can handle the upgrade logic here.
        // For simplicity, we'll just drop the existing table and recreate it.
        db.execSQL("DROP TABLE IF EXISTS drawing_table;");
        onCreate(db);
    }
}