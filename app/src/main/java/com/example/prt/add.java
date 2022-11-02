package com.example.prt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class add extends AppCompatActivity {

    private EditText etName, etSurname, etPatronymic, etSubject;
    private Button AddTeacher,btnAddd,btnDel;
    private ImageView Picture;
    private TextView status;
    String img=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPatronymic = findViewById(R.id.etPatronymic);
        etSubject = findViewById(R.id.etSubject);
        AddTeacher = findViewById(R.id.AddTeacher);
        btnAddd = findViewById(R.id.btnAddd);
        btnDel = findViewById(R.id.btnDel);
        Picture = findViewById(R.id.Picture);
        status = findViewById(R.id.status);


    }

    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data) {
        try {
            super.onActivityResult(request, result, data);
            if (request == 1 && data != null && data.getData() != null) {
                if (result == RESULT_OK) {
                    Log.d("MyLog", "Image URI : " + data.getData());
                    Picture.setImageURI(data.getData());
                    Bitmap bitmap = ((BitmapDrawable) Picture.getDrawable()).getBitmap();
                    encodeImg(bitmap);

                }
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так с изображением", Toast.LENGTH_LONG).show();
        }
    }

    public String encodeImg(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";

    }

    public void AddingDataFromSQL(View v)
    {
        try{
            if (etName.getText().length() == 0 ||
                    etSurname.getText().length() == 0||
                    etPatronymic.getText().length() == 0 ||
                    etSubject.getText().length() == 0) {
                String responseString = "Не все поля заполнены!!";
                status.setText(responseString);
                return;
            }
            postData(etName.getText().toString(), etSurname.getText().toString(), etPatronymic.getText().toString(), etSubject.getText().toString(), img.toString());
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так с заполнением полей", Toast.LENGTH_LONG).show();
        }
    }



    private void postData(String name, String surname, String patronymic, String subject, String iimg)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5101/NGKNN/лебедевааф/api/Teachers").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        DataModal modal = new DataModal(name, surname, patronymic,subject, iimg);
        Call<DataModal> call = retrofitAPI.createPost(modal);
        call.enqueue((new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(add.this,"Data added to AP", Toast.LENGTH_LONG).show();

                DataModal responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getSurname() + "\n" + "Job : " + responseFromAPI.getPatronymic() + "\n" + "Job : " + responseFromAPI.getSubject();
                status.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        }));
    }



    public void onClickImage(View view) //добавление картинки
    {
        try {
            Intent intentChooser = new Intent();
            intentChooser.setType("image/*");
            intentChooser.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentChooser, 1);
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }

    }


}