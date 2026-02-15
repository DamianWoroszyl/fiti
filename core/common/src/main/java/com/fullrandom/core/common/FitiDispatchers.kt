package com.fullrandom.core.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: FitiDispatchers)

enum class FitiDispatchers {
    Default,
    Io,
    Main
}
