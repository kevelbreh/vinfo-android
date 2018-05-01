package com.github.kevelbreh.vinfo;

import android.app.Application;

import com.github.kevelbreh.vinfo.data.VehicleService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class VinfoApplication extends Application {

    private VehicleService service;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        service = new Retrofit.Builder()
                .baseUrl("http://192.168.0.100:3000/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(VehicleService.class);
    }

    public VehicleService getVehicleService() {
        return service;
    }
}
