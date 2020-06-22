package com.example.water.presentation.model

import com.example.water.data.model.WeatherData
import com.example.water.data.repository.IRepository
import com.example.water.util.State
import com.example.water.util.parseState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherModel
@Inject constructor(
    private val repository: IRepository
) {

    suspend fun fetchWeatherData(lat: Double, lon: Double): State<WeatherData> =
        withContext(Dispatchers.IO) {
            try {
                repository.fetchWeatherData(lat, lon).parseState()
            } catch (ex: Exception) {
                State.Error<WeatherData>(ex)
            }
        }
}