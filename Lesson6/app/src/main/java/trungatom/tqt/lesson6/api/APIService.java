package trungatom.tqt.lesson6.api;

import android.annotation.SuppressLint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import trungatom.tqt.lesson6.models.GetWeatherRespond;

@SuppressLint("StaticFieldLeak")
public interface APIService {

    @GET("weather")

    Call<GetWeatherRespond> getWeather(
            @Query("q") String nameCity,
            @Query("units") String temperatureType,
            @Query("appid") String apiKey
    );

}
