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
    private Button btnAddd, btnDel, btnEditing;
    private EditText eName, eSurname, ePatronymic, eSubject;
    private TextView status;
    String img = null;
    private List<Mask> listProduct = new ArrayList<>();
    Mask mask;
    String Image;
    DataModal dataModal;
    int id;
    Bundle arg;
    Integer kod_teacher;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing);

        arg = getIntent().getExtras();

        id = arg.getInt("Kod_teacher");
        dataModal = arg.getParcelable(DataModal.class.getSimpleName());

        Picture=findViewById(R.id.Picture);
       Picture.setImageBitmap(getImgBitmap(dataModal.getImages()));

        eName = findViewById(R.id.eName);
        eName.setText(dataModal.getName());
        eSurname = findViewById(R.id.eSurname);
        eSurname.setText(dataModal.getSurname());
        ePatronymic = findViewById(R.id.ePatronymic);
        ePatronymic.setText(dataModal.getPatronymic());
        eSubject = findViewById(R.id.eSubject);
        eSubject.setText(dataModal.getSubject());


        status = findViewById(R.id.status);
        btnAddd = findViewById(R.id.btnAddd);
        btnDel = findViewById(R.id.btnDel);
        btnEditing = findViewById(R.id.btnEditing);


    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null  && result != null) {
                try {
                    Uri uri = result.getData().getData();
                    InputStream is = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Picture.setImageBitmap(bitmap);
                    img = encodeImg(bitmap);
                } catch (Exception e) {
                    Toast.makeText(editing.this,"Что-то пошло не так  с изображением", Toast.LENGTH_LONG).show();
                }
            }
        }
    });



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
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pickImg.launch(intent);
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с добавлением фото", Toast.LENGTH_LONG).show();
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
            Toast.makeText(editing.this,"Что-то пошло не так с удалением фото", Toast.LENGTH_LONG).show();
        }
    }


    private void  putUpdate (DataModal dataModal, View v)
    {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://ngknn.ru:5001/NGKNN/лебедевааф/api/").addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<DataModal> call = retrofitAPI.updateData( dataModal.getKod_teacher(), dataModal);
        call.enqueue(new Callback<DataModal>()
        {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                String responseString = "Данные успешно изменены";
                status.setText(responseString);


            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

            }
        });
    }

    public void Update (View v)
    {
        try{
//            dataModal.setName(eName.getText().toString());
//            dataModal.setSurname(eSurname.getText().toString());
//            dataModal.setPatronymic(ePatronymic.getText().toString());
//            dataModal.setSubject(eSubject.getText().toString());
//            dataModal.setImages();
//            String Name = eName.getText().toString();
//            String Surname = eSurname.getText().toString();
//            String Patronymic = ePatronymic.getText().toString();
//            String Subject = eSubject.getText().toString();
            if (eName.getText().length() == 0 ||
                    eSurname.getText().length() == 0||
                    ePatronymic.getText().length() == 0 ||
                   eSubject.getText().length() == 0) {
                String responseString = "Не все поля заполнены!!";
                status.setText(responseString);
                return;
            }
                putUpdate(dataModal, v);
            SystemClock.sleep(1000);
            goBack(v);
        }
        catch (Exception ex)
        {
            Toast.makeText(editing.this,"Что-то пошло не так  с заполнением полей и добавлением", Toast.LENGTH_LONG).show();
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

                }

                @Override
                public void onFailure(Call<DataModal> call, Throwable t) {

                }
            });
            SystemClock.sleep(500);
            startActivity(new Intent(this, conclusion.class));
        }
        catch (Exception ex) {
            Toast.makeText(editing.this, "Что-то пошло не так с удалением", Toast.LENGTH_LONG).show();
        }
    }


}