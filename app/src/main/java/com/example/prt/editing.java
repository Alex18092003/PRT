package com.example.prt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class editing extends AppCompatActivity {

    private ImageView Picture;
    Bitmap bitmap=null;
    private EditText eName, eSurname, ePatronymic, eSubject;
    private TextView status;
    String img = null;
    private List<Mask> listProduct = new ArrayList<>();
    DataModal dataModal;
    Bundle arg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        arg = getIntent().getExtras();
        dataModal = arg.getParcelable(DataModal.class.getSimpleName());

        Picture=findViewById(R.id.Picture);


        eName = findViewById(R.id.eName);
        eName.setText(dataModal.getName());
        eSurname = findViewById(R.id.eSurname);
        eSurname.setText(dataModal.getSurname());
        ePatronymic = findViewById(R.id.ePatronymic);
        ePatronymic.setText(dataModal.getPatronymic());
        eSubject = findViewById(R.id.eSubject);
        eSubject.setText(dataModal.getSubject());

        AdapterMask decodeImage = new AdapterMask(editing.this);
        Picture.setImageBitmap(decodeImage.getUserImage(dataModal.getImages()));
        if(!dataModal.getImages().equals("null")){
            bitmap = decodeImage.getUserImage(dataModal.getImages());
        }
        else
        {
            bitmap = null;
        }
        status = findViewById(R.id.status);
    }

    public void deletePicture(View v) // удаление изображения, кнопка "Удалить фото"
    {
        try {
            Picture.setImageBitmap(null);
            Picture.setImageResource(R.drawable.nophoto);
            bitmap = null;
        }
        catch (Exception ex)
        {
            Toast.makeText(editing.this,"Что-то пошло не так с удалением фото", Toast.LENGTH_LONG).show();
        }
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    Picture.setImageURI(uri);
                } catch (Exception e) {
                    Toast.makeText(editing.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
            }
        }
    });

    public String Image(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        } else {
            String imgg = encodeImg(bitmap);
            return imgg;
        }
    }
    public String encodeImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img = java.util.Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";
    }

    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        try {
            SystemClock.sleep(1000);
            Intent intent = new Intent(this, conclusion.class);
            startActivity(intent);
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с переходом на страницы", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickImage(View view) //добавление картинки
    {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImg.launch(intent);
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с добавлением фото", Toast.LENGTH_LONG).show();
        }
    }

    //проверка заполнения полей для обновления
    public void Update (View v)
    {
        try{
            dataModal.setName(eName.getText().toString());
            dataModal.setSurname(eSurname.getText().toString());
            dataModal.setPatronymic(ePatronymic.getText().toString());
            dataModal.setSubject(eSubject.getText().toString());
            dataModal.setImages(Image(bitmap));
            if (eName.getText().length() == 0 ||
                    eSurname.getText().length() == 0||
                    ePatronymic.getText().length() == 0 ||
                    eSubject.getText().length() == 0) {
                String responseString = "Не все поля заполнены!!";
                status.setText(responseString);
                return;
            }
            else {
                putUpdate(dataModal, v);
                SystemClock.sleep(1500);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(editing.this,"Что-то пошло не так  с заполнением полей и изменением", Toast.LENGTH_LONG).show();
        }
    }
//обновление данных
    private void  putUpdate ( DataModal dataModal, View v)
    {
        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/лебедевааф/api/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<DataModal> call = retrofitAPI.updateData(dataModal.getKod_teacher(),dataModal);
            call.enqueue(new Callback<DataModal>() {
                @Override
                public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                    String responseString = "Данные успешно изменены";
                    status.setText(responseString);
                }
                @Override
                public void onFailure(Call<DataModal> call, Throwable t) {
                    Toast.makeText(editing.this, "Что-то пошло не так с изменением", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с изменением", Toast.LENGTH_LONG).show();
        }
    }

    // согласие на удаление
    public void Delet (View v)
    {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(editing.this);
            builder.setTitle("Удалить")
                    .setMessage("Вы уверены что хотите удалить данные")
                    .setCancelable(false)
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Delete(dataModal, v);
                            goBack(v);
                        }
                    })
                    .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с согласием на удаление", Toast.LENGTH_LONG).show();
        }
    }

    // удаление
    public void Delete(DataModal dataModal, View v)
    {
        try {
            Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/лебедевааф/api/").addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            Call<DataModal> call = retrofitAPI.deleteData(dataModal.getKod_teacher());
            call.enqueue(new Callback<DataModal>() {
                @Override
                public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                    Toast.makeText(editing.this, "Успешное удаление", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Call<DataModal> call, Throwable t) {
                    Toast.makeText(editing.this, "Что-то пошло не так с удалением", Toast.LENGTH_LONG).show();
                }
            });
            SystemClock.sleep(600);
            startActivity(new Intent(this, conclusion.class));
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с удалением", Toast.LENGTH_LONG).show();
        }
    }
}