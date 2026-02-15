package com.fullrandom.fiti.core.ui.navigation

import com.fullrandom.core.common.Dispatcher
import com.fullrandom.core.common.FitiDispatchers
import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import com.fullrandom.fiti.core.ui.api.navigation.NavigationAction
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch

class NavigatorImpl @Inject constructor(
    @Dispatcher(FitiDispatchers.Default) defaultDispatcher: CoroutineDispatcher,
) : Navigator {

    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    private val _navigationChannel: Channel<NavigationAction> = Channel(capacity = UNLIMITED)
    override val navigationChannel: ReceiveChannel<NavigationAction> = _navigationChannel

    override fun navigate(
        destination: FitiNavKey
    ) {
        coroutineScope.launch {
            _navigationChannel.send(NavigationAction.Navigate(destination))
        }
    }

    override fun pop() {
        coroutineScope.launch {
            _navigationChannel.send(NavigationAction.Back)
        }
    }
}