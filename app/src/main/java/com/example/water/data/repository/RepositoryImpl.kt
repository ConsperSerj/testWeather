package com.example.water.data.repository

import com.example.water.BuildConfig
import com.example.water.data.model.WeatherData
import com.example.water.data.service.IApiService
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl
@Inject constructor(
    private val apiService: IApiService
) : IRepository {

    override suspend fun fetchWeatherData(lat: Double, lon: Double): Response<WeatherData> =
        apiService.fetchWeatherData(lat, lon, BuildConfig.API_KEY)
}