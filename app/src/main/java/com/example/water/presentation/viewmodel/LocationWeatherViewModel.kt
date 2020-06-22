package com.example.water.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.water.data.model.WeatherData
import com.example.water.presentation.model.WeatherModel
import com.example.water.util.State
import com.example.water.util.inProgress
import kotlinx.coroutines.launch

class LocationWeatherViewModel
@ViewModelInject constructor(
    private val model: WeatherModel
) : ViewModel() {

    private val weatherDataLiveData by lazy { MutableLiveData<State<WeatherData>>() }
    val weather: LiveData<State<WeatherData>> get() = weatherDataLiveData

    fun loadWeatherData(lat: Double, lon: Double) {
        viewModelScope.launch {
            weatherDataLiveData.postValue(weatherDataLiveData.value.inProgress())
            weatherDataLiveData.postValue(model.fetchWeatherData(lat, lon))
        }
    }
}