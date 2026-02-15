package com.fullrandom.fiti.core.ui.api.navigation

import kotlinx.coroutines.channels.ReceiveChannel

interface Navigator {

    val navigationChannel: ReceiveChannel<NavigationAction>

    fun navigate(
        destination: FitiNavKey
    )

    fun pop()

}