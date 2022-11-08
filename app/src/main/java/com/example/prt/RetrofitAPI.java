package com.example.prt;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    // так как мы отправляем запрос на публикацию данных
    // так что мы аннотируем его постом
    // и вместе с этим мы передаем параметр как пользователи
    @POST("users")
//в строке ниже мы создаем метод для публикации наших данных.
    Call<DataModal> createPost(@Body DataModal dataModal);

    @PUT("Teachers/")
    Call<DataModal> updateData(@Query("Kod_teacher") int Kod_teacher, @Body DataModal dataModal);

    @DELETE("Teachers/{id}")
    Call<Void> deleteData(@Path("id") int id);
}
