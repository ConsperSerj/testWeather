package com.example.water.data.repository

import com.example.water.data.model.WeatherData
import retrofit2.Response

interface IRepository {
    suspend fun fetchWeatherData(lat: Double, lon: Double): Response<WeatherData>
}