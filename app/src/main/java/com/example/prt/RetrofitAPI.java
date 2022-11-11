package com.example.prt;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("users")
    Call<DataModal> createPost(@Body DataModal dataModal);

    @PUT("Teachers/{id}")
    Call<DataModal> updateData(@Path("id") int id, @Body DataModal dataModal);


    @DELETE("Teachers/{id}")
    Call<DataModal> deleteData(@Path("id") int id);
}
