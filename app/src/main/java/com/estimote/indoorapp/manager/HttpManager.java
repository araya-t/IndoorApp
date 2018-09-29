package com.estimote.indoorapp.manager;

import android.content.Context;
import android.util.Log;

import com.estimote.indoorapp.manager.http.ApiServiceGMS;
import com.estimote.indoorapp.manager.http.ApiServiceParka;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private static HttpManager instance;
    private Context mContext;
    private static ApiServiceParka serviceParka = null;
    private static ApiServiceGMS serviceGMS = null;
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

        serviceParka = retrofitParka.create(ApiServiceParka.class);
        serviceGMS = retrofitGMS.create(ApiServiceGMS.class);

        Log.d("HttpManager", "-----> serviceParka = " + serviceParka);
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

    public ApiServiceParka getServiceParka() {
        return serviceParka;
    }

    public ApiServiceGMS getServiceGMS() {
        return serviceGMS;
    }
}
