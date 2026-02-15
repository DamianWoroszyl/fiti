package com.fullrandom.fiti.di

import com.fullrandom.core.common.DispatchersModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DispatchersModule::class,
    ]
)
object AppModule {
}