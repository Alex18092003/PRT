package com.example.prt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class conclusion extends AppCompatActivity {


    private AdapterMask pAdapter;
    private List<Mask> listProduct = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclusion);

        ListView ivProducts = findViewById(R.id.ListProduct);
        pAdapter = new AdapterMask(conclusion.this, listProduct);
        ivProducts.setAdapter(pAdapter);

        new GetTeacher().execute();
    }

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
                pAdapter.notifyDataSetInvalidated();
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Mask tempTeacher = new Mask(
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

                Toast.makeText(conclusion.this, "Что-то пошло не так", Toast.LENGTH_LONG).show();
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
            Toast.makeText(conclusion.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
    }
}