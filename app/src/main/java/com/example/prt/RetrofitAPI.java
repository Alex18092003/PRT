package com.example.prt;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;

public interface RetrofitAPI {
    // так как мы отправляем запрос на публикацию данных
    // так что мы аннотируем его постом
    // и вместе с этим мы передаем параметр как пользователи
    @POST("users")
//в строке ниже мы создаем метод для публикации наших данных.
    Call<DataModal> createPost(@Body DataModal dataModal);
}
