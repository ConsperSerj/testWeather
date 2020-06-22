package com.example.water.data.model

data class WeatherData(
    val latitude: Float,
    val longitude: Float,
    val timezone: String,
    val currently: Currently,
    val hourly: Hourly,
    val daily: Daily,
    val flags: Flags,
    val offset: Int
)










