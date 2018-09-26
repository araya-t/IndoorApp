package com.estimote.indoorapp.Manager;

import android.content.Context;
import android.util.Log;

import com.estimote.indoorapp.Manager.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager instance;
    private Context mContext;
    private static ApiService serviceParka = null;
    private static ApiService serviceGMS = null;
    private String url_parka = "https://applicationserver.parka028.me/";
    private String url_gms = "https://gms.parka028.me/";

    public static HttpManager getInstance(){
        if (instance == null)
            instance = new HttpManager();
        return instance;
    }

    private HttpManager(){
        mContext = Contextor.getInstance().getContext();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ") // Can Change Format
                .create();

        Retrofit retrofitParka = getRetrofitParka(gson);
        Retrofit retrofitGMS = getRetrofitGMS(gson);

        serviceParka = retrofitParka.create(ApiService.class);
        Log.d("HttpManager", "-----> serviceParka = " + serviceParka);

        serviceGMS = retrofitGMS.create(ApiService.class);
        Log.d("HttpManager", "-------> serviceGMS = " + serviceGMS);


        Log.d("HttpManager", "create HTTP Manager");

    }

    public Retrofit getRetrofitParka(Gson gson){
        return new Retrofit.Builder()
                .baseUrl(url_parka)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofitGMS(Gson gson){
        return new Retrofit.Builder()
                .baseUrl(url_gms)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public ApiService getServiceParka() {
        return serviceParka;
    }

    public ApiService getServiceGMS() {
        return serviceGMS;
    }
}
