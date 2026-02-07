package com.fullrandom.fiti.core.ui.api.navigation

sealed class NavigationAction {

    class Navigate(
        val navigationKey: FitiNavKey
    ): NavigationAction()

    data object Back: NavigationAction()

}