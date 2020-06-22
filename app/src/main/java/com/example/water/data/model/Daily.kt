package com.example.water.data.model

data class Daily(
    val summary: String,
    val icon: String,
    val data: List<DailyData>
)