package com.example.water.di

import com.example.water.feature.DefaultNetworkCachePolicyFeature
import com.example.water.feature.INetworkCachePolicyFeature
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@[Module InstallIn(ApplicationComponent::class)]
interface FeatureModule {

    @Binds
    fun bindNetworkCachePolicyFeature(impl: DefaultNetworkCachePolicyFeature): INetworkCachePolicyFeature
}