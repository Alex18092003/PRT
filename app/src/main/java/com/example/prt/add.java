package com.example.prt;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    String img = null;


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

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Picture.setImageBitmap(bitmap);
                    img = encodeImg(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    });

    public String encodeImg(Bitmap bitmap) {
        int prevW = 500;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();

        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return img = "";
    }

    public void AddingDataFromSQL(View v)
    {
        try{
            String Name = etName.getText().toString();
            String Surname = etSurname.getText().toString();
            String Patronymic = etPatronymic.getText().toString();
            String Subject = etSubject.getText().toString();
            if (etName.getText().length() == 0 ||
                    etSurname.getText().length() == 0||
                    etPatronymic.getText().length() == 0 ||
                    etSubject.getText().length() == 0) {
                String responseString = "Не все поля заполнены!!";
                status.setText(responseString);
                return;
            }
            else {
                    postData(Name, Surname, Patronymic, Subject, img);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так  с добавлением данных", Toast.LENGTH_LONG).show();
        }
    }



    private void postData(String name, String surname, String patronymic, String subject, String picture)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/лебедевааф/api/Teachers/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        DataModal modal = new DataModal(name, surname, patronymic,subject, picture);
        Call<DataModal> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                String responseString = "Данные успешно добавлены";
                status.setText(responseString);
                etName.setText("");
                etSurname.setText("");
                etPatronymic.setText("");
                etSubject.setText("");
                Picture.setImageResource(R.drawable.nophoto);
                img = null;
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    private void getImage()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }




    public void onClickImage(View view) //добавление картинки
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        try {
            Intent intent = new Intent(this, conclusion.class);
            startActivity(intent);
        }
        catch (Exception ex) {
            Toast.makeText(add.this, "Что-то пошло не так с переходом на страницы", Toast.LENGTH_LONG).show();
        }
    }

    public void deletePicture(View v) // удаление изображения, кнопка "Удалить фото"
    {
        try {
            Picture.setImageBitmap(null);
            Picture.setImageResource(R.drawable.nophoto);
            img = null;
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так с удалением фото", Toast.LENGTH_LONG).show();
        }

    }

    public  void CleaningOfAllFields(View v) // очистка всех полей, кнопка "Очистить"
    {
        try {
            etName.setText("");
            etSurname.setText("");
            etPatronymic.setText("");
            etSubject.setText("");
            status.setText("Введите данные");
            Picture.setImageResource(R.drawable.nophoto);
            img = null;
        }
        catch (Exception ex)
        {
            Toast.makeText(add.this,"Что-то пошло не так с очисткой полей", Toast.LENGTH_LONG).show();
        }

    }

}