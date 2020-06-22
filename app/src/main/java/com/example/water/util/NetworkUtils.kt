package com.example.water.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import javax.inject.Inject

class NetworkUtils @Inject constructor(
    context: Context
) {

    private val connectivityManager: ConnectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isConnected(): Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.activeNetwork != null
    } else {
        connectivityManager.activeNetworkInfo.isConnected
    }
}