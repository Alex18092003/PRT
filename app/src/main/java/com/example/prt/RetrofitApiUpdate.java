package com.example.prt;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface RetrofitApiUpdate {
    @PUT("Teachers/2")
    Call<DataModal> updateData(@Body DataModal dataModal);
}
