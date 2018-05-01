package com.github.kevelbreh.vinfo.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VehicleService {

    @GET("vehicle/{vin}")
    Single<VehicleInfo> getVehicle(@Path("vin") String vin);
}
