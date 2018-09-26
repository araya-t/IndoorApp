package com.estimote.indoorapp.Manager.http;

import com.estimote.indoorapp.Model.dao.CarPositions;
import com.estimote.indoorapp.Model.dao.Token;
import com.estimote.indoorapp.Model.dao.User;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
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

    //send trigger to GMS to change the status of parking slot
    @FormUrlEncoded
    @PUT("positions/{position_id}/parking")
    Call changeStatus(@Path("position_id") int position_id,
                      @Field("is_available") String is_available);

    //send data of position when car stopped to App Server
    @FormUrlEncoded
    @PUT("user/car/{token}")
    Call<CarPositions> sendXYPosition(@Path("token") String userToken,
                                      @Field("fcm_token") String fcm_token,
                                      @Field("timestamp_stop_engine") long timestampLong1000,
                                      @Field("flood_id") int flood_id,
                                      @Field("x") double x_position,
                                      @Field("y") double y_position );

}
