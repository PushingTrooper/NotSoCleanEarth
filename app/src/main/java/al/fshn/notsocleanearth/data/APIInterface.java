package al.fshn.notsocleanearth.data;

import al.fshn.notsocleanearth.MyApplication;
import al.fshn.notsocleanearth.data.model.AirPollutionForecast;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("air_pollution/forecast")
    Call<AirPollutionForecast> getFuturePollution(
            @Query("lat") Double latitude,
            @Query("lon") Double longitude,
            @Query("appid") String appId
    );
}