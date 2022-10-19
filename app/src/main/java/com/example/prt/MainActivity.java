package com.example.prt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public  void  goTeacher(View view) // переход в окно выводы информации
    {
        try {
            Intent intent = new Intent(this, conclusion.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
    }
}