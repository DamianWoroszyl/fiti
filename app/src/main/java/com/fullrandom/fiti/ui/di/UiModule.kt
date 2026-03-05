package com.fullrandom.fiti.ui.di

import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.fiti.core.ui.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UiModule {

    @Singleton
    @Provides
    fun provideNavigator(navigatorImpl: NavigatorImpl): Navigator = navigatorImpl

}