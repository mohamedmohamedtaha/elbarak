package com.muslimcharityapps.elbarak.retro;

import com.muslimcharityapps.elbarak.model.WeatherBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by JAYMATAJI on 7/3/2016.
 */
public interface RetroWS {

    @GET("weather")
    Observable<WeatherBean> getWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("units") String temperature, @Query("appid") String appid);

    @GET("weather")
    Observable<WeatherBean> getWeatherByPinCode(@Query("zip") String zipCode, @Query("units") String temperature, @Query("appid") String appid);

    @GET
    @Streaming
    Observable<Response<ResponseBody>> downloadFileFromServer(@Url String serverUrl);
}
