package com.example.prt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class add extends AppCompatActivity {

    private EditText Name, Surname, Patronymic, Subject;
    private Button AddTeacher,btnAddd,btnDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Name = findViewById(R.id.Name);
        Surname = findViewById(R.id.Surname);
        Patronymic = findViewById(R.id.Patronymic);
        Subject = findViewById(R.id.Subject);
        AddTeacher = findViewById(R.id.AddTeacher);
        btnAddd = findViewById(R.id.btnAddd);
        btnDel = findViewById(R.id.btnDel);

        AddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( Name.getText().toString().isEmpty() &&
                        Surname.getText().toString().isEmpty() &&
                        Patronymic.getText().toString().isEmpty() &&
                        Subject.getText().toString().isEmpty())
                {
                    Toast.makeText(add.this,"Не все поля заполнены", Toast.LENGTH_LONG).show();
                    return;
                }
                postData(Name.getText().toString(), Surname.getText().toString(), Patronymic.getText().toString(), Subject.getText().toString());
            }
        });
    }

    private void postData(String Name, String Surname, String Patronymic, String Subject)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5101/NGKNN/лебедевааф/api/Teachers").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        DataModal modal = new DataModal(Name, Surname, Patronymic,Subject );
        Call<DataModal> call = retrofitAPI.createPost(modal);
        call.enqueue((new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(add.this,"Data added to AP", Toast.LENGTH_LONG).show();

                DataModal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        }));

    }

}