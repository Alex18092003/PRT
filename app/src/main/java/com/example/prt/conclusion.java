package com.example.prt;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class conclusion extends AppCompatActivity {

    private Spinner spinner;
    private AdapterMask pAdapter;
    ListView lvProduct;
    private List<DataModal> listProduct = new ArrayList<>();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion);

        lvProduct = findViewById(R.id.ListProduct);
        ListView ivProducts = findViewById(R.id.ListProduct);
        pAdapter = new AdapterMask(conclusion.this, listProduct);
        ivProducts.setAdapter(pAdapter);

        List<String> list = new ArrayList<String>();
        list.add("Без сортировки");
        list.add("Сортировка по именам");
        list.add("Сортировка по фамилии");

        spinner = findViewById(R.id.Spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            SortNameSurname(listProduct);
        }
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
        }

    });
        new GetTeacher().execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.N) //сортировка
    public void SortNameSurname(List<DataModal> list){
        try {
            lvProduct.setAdapter(null);
            switch (spinner.getSelectedItemPosition()) {
                case 0:
                    new GetTeacher().execute();
                    break;
                case 1:
                    Collections.sort(list, Comparator.comparing(DataModal::getName));
                    break;
                case 2:
                    Collections.sort(list, Comparator.comparing(DataModal::getSurname));
                    break;
                default:
                    break;
            }
            pAdapter = new AdapterMask(conclusion.this, list);
            lvProduct.setAdapter(pAdapter);
            pAdapter.notifyDataSetInvalidated();
        }
        catch (Exception ex) {

            Toast.makeText(conclusion.this, "Что-то пошло не так с сортировкой", Toast.LENGTH_LONG).show();
        }
    }

    // вывод данных
    private class GetTeacher extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/лебедевааф/api/Teachers");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                listProduct.clear();
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    DataModal tempTeacher = new DataModal(
                            productJson.getInt("Kod_teacher"),
                            productJson.getString("Name"),
                            productJson.getString("Surname"),
                            productJson.getString("Patronymic"),
                            productJson.getString("Subject"),
                            productJson.getString("Images")
                    );
                    listProduct.add(tempTeacher);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ex) {

                Toast.makeText(conclusion.this, "Что-то пошло не так с выводом данных", Toast.LENGTH_LONG).show();
            }
        }
    }

    public  void  goAdd(View view) // переход в окно ввода информации
    {
        try {
            Intent intent = new Intent(this, add.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(conclusion.this,"Что-то пошло не так с переходом", Toast.LENGTH_LONG).show();
        }
    }
}