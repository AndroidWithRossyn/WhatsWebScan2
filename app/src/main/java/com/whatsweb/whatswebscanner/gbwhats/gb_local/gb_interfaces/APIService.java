package com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_interfaces;



import com.whatsweb.whatswebscanner.gbwhats.gb_local.gb_model.gb_Tegres;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("key_Application")
    @FormUrlEncoded
    Call<gb_Tegres> TEGRES_CALL(@Field("key_id") int i);

}
