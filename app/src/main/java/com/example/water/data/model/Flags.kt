package com.example.water.data.model

import com.squareup.moshi.Json

data class Flags(
    val sources: List<String>,
    @Json(name = "meteoalarm-license")
    val meteoalarmLicense: String,
    @Json(name = "nearest-station")
    val nearestStation: Float,
    val units: String

)