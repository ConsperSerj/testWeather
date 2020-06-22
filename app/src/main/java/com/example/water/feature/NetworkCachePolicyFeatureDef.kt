package com.example.water.feature

import com.example.water.util.NetworkUtils
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface INetworkCachePolicyFeature : IFeature {

    fun getCachePolicy(): CacheControl
}


class DefaultNetworkCachePolicyFeature
@Inject constructor(
    private val networkUtils: NetworkUtils
) : INetworkCachePolicyFeature {

    override val isEnabled: Boolean = true

    override val params: Any? = null

    override fun getCachePolicy() = CacheControl.Builder()
        .let {
            if (networkUtils.isConnected())
                it.maxAge(CACHE_MAX_AGE, TimeUnit.SECONDS)
            else it.onlyIfCached().maxStale(Int.MAX_VALUE, TimeUnit.SECONDS)
        }
        .build()


    companion object {
        private const val CACHE_MAX_AGE = 60// * 60
    }
}

class EmptyNetworkCachePolicyFeature
@Inject constructor(delegate: DefaultNetworkCachePolicyFeature) : INetworkCachePolicyFeature {

    override val isEnabled: Boolean = delegate.isEnabled

    override val params = delegate.params

    override fun getCachePolicy() = CacheControl.FORCE_NETWORK
}

private fun Map<String, Any?>.joinToString() =
    map { entry -> entry.value?.let { "${entry.key}=$it" } ?: entry.key }.joinToString()
