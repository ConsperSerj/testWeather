package com.example.water.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@[Module InstallIn(ApplicationComponent::class)]
interface ApplicationModule {

    @Binds
    fun bindContext(app: Application): Context
}