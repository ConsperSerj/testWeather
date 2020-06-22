package com.example.water.di

import android.content.Context
import com.example.water.BuildConfig
import com.example.water.data.repository.IRepository
import com.example.water.data.repository.RepositoryImpl
import com.example.water.data.service.IApiService
import com.example.water.feature.INetworkCachePolicyFeature
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton

@[Module InstallIn(ApplicationComponent::class)]
interface NetworkModule {

    @[Binds Singleton]
    fun provideRepository(impl: RepositoryImpl): IRepository

    companion object {

        @Provides
        fun provideBaseApiUrl(): String = BuildConfig.API_BASE_URL

        @Provides
        fun provideOkHttpCache(context: Context): Cache =
            Cache(File(context.cacheDir, "responses"), 5 * 1024 * 1024)

        @Provides
        fun provideOkHttpClient(
            cache: Cache,
            cachePolicyFeature: INetworkCachePolicyFeature
        ): OkHttpClient =
            OkHttpClient.Builder().apply {

                if (BuildConfig.DEBUG) {
                    val logger = HttpLoggingInterceptor()
                    logger.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logger)
                }

                val interceptor = object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response =
                        chain.request().let { request ->
                            val newRequest = request.newBuilder()
                                .cacheControl(cachePolicyFeature.getCachePolicy())
                                .build()
                            CacheControl.FORCE_NETWORK
                            return@let chain.proceed(newRequest)
                        }

                }
                addNetworkInterceptor(interceptor)

            }.cache(cache).build()

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()

        @Provides
        fun provideService(retrofit: Retrofit): IApiService =
            retrofit.create(IApiService::class.java)
    }
}