package com.example.prt;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class editing extends AppCompatActivity {

    private ImageView Picture;
    private Button btnAddd, btnDel, btnEditing;
    private EditText eName, eSurname, ePatronymic, eSubject;
    private TextView status;
    String img = null;
    Mask mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        mask=getIntent().getParcelableExtra("Teachers");

        Picture=findViewById(R.id.Picture);


        eName = findViewById(R.id.eName);
        eName.setText(mask.getName());
        eSurname = findViewById(R.id.eSurname);
        eSurname.setText(mask.getName());
        ePatronymic = findViewById(R.id.ePatronymic);
        ePatronymic.setText(mask.getName());
        eSubject = findViewById(R.id.eSubject);
        eSubject.setText(mask.getName());
        status = findViewById(R.id.status);
        Picture = findViewById(R.id.Picture);
        btnAddd = findViewById(R.id.btnAddd);
        btnDel = findViewById(R.id.btnDel);
        btnEditing = findViewById(R.id.btnEditing);
        Picture.setImageBitmap(getImgBitmap(mask.getImages()));
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

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else{
            return BitmapFactory.decodeResource(editing.this.getResources(),
                    R.drawable.nophoto);
        }

    }

    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        try {
            Intent intent = new Intent(this, conclusion.class);
            startActivity(intent);
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с переходом на страницы", Toast.LENGTH_LONG).show();
        }
    }
    public void onClickImage(View view) //добавление картинки
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
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
            Toast.makeText(editing.this,"Что-то пошло не так с удалением фото", Toast.LENGTH_LONG).show();
        }
    }

    private void getImages()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    public void  putUpdate (String name, String surname, String patronymic, String subject, String picture)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/лебедевааф/api/Teachers/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        DataModal modal = new DataModal(name, surname, patronymic,subject, picture);
        Call<DataModal> call = retrofitAPI.updateData(mask.getKod_teacher(), modal);
        call.enqueue(new Callback<DataModal>()
        {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                String responseString = "Данные успешно изменены";
                status.setText(responseString);
                DataModal responseFromAPI = response.body();

            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    public void Update (View v)
    {
        try{
            String Name = eName.getText().toString();
            String Surname = eSurname.getText().toString();
            String Patronymic = ePatronymic.getText().toString();
            String Subject = eSubject.getText().toString();
            if (eName.getText().length() == 0 ||
                    eSurname.getText().length() == 0||
                    ePatronymic.getText().length() == 0 ||
                   eSubject.getText().length() == 0) {
                String responseString = "Не все поля заполнены!!";
                status.setText(responseString);
                return;
            }
            else {
                putUpdate(Name, Surname, Patronymic, Subject, img);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(editing.this,"Что-то пошло не так  с добавлением данных", Toast.LENGTH_LONG).show();
        }
    }

    public void Delet (View v)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(editing.this);
        builder.setTitle("Удалить")
                .setMessage("Вы уверены что хотите удалить данные")
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Delete();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void Delete()
    {

    }


}