package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        databaseHelper.create_db();
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
      /*  db = databaseHelper.open();

        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_GAME, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[]{DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_NAMEM};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        userList.setAdapter(userAdapter);*/
    }

    // по нажатию на кнопку запускаем UserActivity для добавления данных
    public void add(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
    public void showMinimobile(View view){
        Intent intent = new Intent(this, MinimobileActivity.class);
        startActivity(intent);
    }
    public void showMysic(View view){
        Intent intent = new Intent(this, MysicActivityView.class);
        startActivity(intent);
    }
    public void showGame(View view){
        Intent intent = new Intent(this, GameActivityView.class);
        startActivity(intent);
    }
    public void showAnalis(View view){
        Intent intent = new Intent(this, AnalysActivity.class);
        startActivity(intent);
    }
    public void showDetachment(View view){
        Intent intent = new Intent(this, DetachmentActivityView.class);
        startActivity(intent);
    }
}