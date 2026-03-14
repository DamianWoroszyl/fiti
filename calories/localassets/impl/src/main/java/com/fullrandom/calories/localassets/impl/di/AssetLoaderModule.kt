package com.fullrandom.calories.localassets.impl.di

import com.fullrandom.calories.localassets.api.AssetLoader
import com.fullrandom.calories.localassets.impl.AssetLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal interface AssetLoaderModule {

    @Binds
    fun bindAssetLoader(impl: AssetLoaderImpl): AssetLoader
}
