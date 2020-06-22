package com.example.water.data.service

import com.example.water.data.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiService {

    @GET("forecast/{api_key}/{lat},{lon}")
    suspend fun fetchWeatherData(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double,
        @Path("api_key") apiKey: String,
        @Query("lang") lang: String = "ru"
    ): Response<WeatherData>
}