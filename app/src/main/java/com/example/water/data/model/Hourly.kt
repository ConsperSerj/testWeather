package com.example.water.data.model

data class Hourly(
    val summary: String,
    val icon: String,
    val data: List<HourlyData>
)