package com.example.water.data.model

data class Currently(
    val time: Long,
    val summary: String,
    val icon: String,
    val precipIntensity: Float,
    val precipProbability: Float,
    val temperature: Float,
    val apparentTemperature: Float,
    val dewPoint: Float,
    val humidity: Float,
    val pressure: Float,
    val windSpeed: Float,
    val windGust: Float,
    val windBearing: Int,
    val cloudCover: Float,
    val uvIndex: Int,
    val visibility: Int,
    val ozone: Float
)