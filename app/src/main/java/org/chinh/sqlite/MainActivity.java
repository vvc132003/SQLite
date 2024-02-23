package org.chinh.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import org.chinh.sqlite.SQLite.AppDatabase;
public class MainActivity extends AppCompatActivity {
    public static AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "doan.sqlite")
                .allowMainThreadQueries()
                .build();
    }
}