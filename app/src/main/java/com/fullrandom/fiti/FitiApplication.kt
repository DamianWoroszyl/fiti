package com.fullrandom.fiti

import android.app.Application
import com.fullrandom.core.common.Dispatcher
import com.fullrandom.core.common.FitiDispatchers
import com.fullrandom.fiti.init.AppInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class FitiApplication : Application() {

    @Inject
    lateinit var appInitializers: Set<@JvmSuppressWildcards AppInitializer>

    @Inject
    @Dispatcher(FitiDispatchers.Default)
    lateinit var defaultDispatcher: CoroutineDispatcher

    lateinit var coroutineScope: CoroutineScope

    override fun onCreate() {
        super.onCreate()
        coroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
        runInitializers()
    }

    private fun runInitializers() {
        coroutineScope.launch {
            appInitializers.forEach {
                it.doInitWork()
            }
        }
    }
}