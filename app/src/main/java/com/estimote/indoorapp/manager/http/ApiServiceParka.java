package com.estimote.indoorapp.manager.http;

import com.estimote.indoorapp.dao.CarPosition;
import com.estimote.indoorapp.dao.Token;
import com.estimote.indoorapp.dao.User;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiServiceParka {
    @FormUrlEncoded
    @POST("users/login/")
    Call<Token> login(@Field("username") String username,
                      @Field("password") String password);

    @GET("users/getUserInfo/{token}/")
    Call<User> getUserInfo(@Path("token") String userToken);

    @FormUrlEncoded
    @POST("users/register/")
    Call<User> register(@Field("name") String name,
                        @Field("surname") String surname,
                        @Field("tel") String tel,
                        @Field("email") String email,
                        @Field("username") String username,
                        @Field("password") String password);

    @DELETE("users/logout/{token}/")
    Call<User> logout(@Path("token") String userToken);

    //send data of position when car stopped to App Server
    @FormUrlEncoded
    @POST("users/car/{token}/")
    Call<CarPosition> sendXYPosition(@Path("token") String token,
                                     @Field("x") double x_position,
                                     @Field("y") double y_position,
                                     @Field("floor_id") int floorId,
                                     @Field("fcm_token") String fcmToken,
                                     @Field("timestamp_stop_engine") long timestampLong1000);
}
