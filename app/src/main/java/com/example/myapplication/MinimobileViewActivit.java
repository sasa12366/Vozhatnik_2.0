package com.example.myapplication;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MinimobileViewActivit extends AppCompatActivity {

        TextView yearBox;
        EditText nameBox;
        // EditText yearBox;
        Button delButton;
        Button saveButton;
        DatabaseHelper sqlHelper;
        SQLiteDatabase db;
        Cursor userCursor;
        long userId=0;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_minimobile_view);
            nameBox = findViewById(R.id.name);
            yearBox = findViewById(R.id.year);
            delButton = findViewById(R.id.deleteButton);
            saveButton = findViewById(R.id.saveButton);
            sqlHelper = new DatabaseHelper(this);
            db = sqlHelper.open();
            yearBox.setMovementMethod(new ScrollingMovementMethod());
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                userId = extras.getLong("id");
            }
            // если 0, то добавление
            if (userId > 0) {
                // получаем элемент по id из бд
                userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_MINI + " where " +
                        DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
                userCursor.moveToFirst();
                // nameBox.setText(userCursor.getString(2));
                yearBox.setText(userCursor.getString(1));
                setTitle(userCursor.getString(0));
                userCursor.close();
            } else {
                // скрываем кнопку удаления
                delButton.setVisibility(View.GONE);
            }}
        //   loadDoc();    }
        public void save(View view){
            ContentValues cv = new ContentValues();
            cv.put(DatabaseHelper.COLUMN_NAMEM, nameBox.getText().toString());
            cv.put(DatabaseHelper.COLUMN_TEXT, yearBox.getText().toString());

            if (userId > 0) {
                db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
            } else {
                db.insert(DatabaseHelper.TABLE, null, cv);
            }
            goHome();
        }
        public void delete(View view){
            db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
            goHome();
        }
        private void goHome(){
            // закрываем подключение
            db.close();
            // переход к главной activity
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        public void avtoScroll(View view){
        yearBox.setMovementMethod(new ScrollingMovementMethod());
            int totalLineCount = yearBox.getLineCount();
            int lineHeight = yearBox.getLineHeight();
            int totlaHeight = totalLineCount * lineHeight;
            MyThread pr=new MyThread();
            pr.start();
        }
    class MyThread extends Thread{
        int totalLineCount = yearBox.getLineCount();
        int lineHeight = yearBox.getLineHeight();
        int totlaHeight = totalLineCount * lineHeight;

        @Override
        public void run() {
            super.run();
            int i=0;
            while (i<totlaHeight) {

                try {
                    Thread.sleep(1000);
                    yearBox.scrollTo(0, i+50);
                    i+=50;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}

