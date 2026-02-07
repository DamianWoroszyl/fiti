package com.fullrandom.fiti.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import com.fullrandom.fiti.core.ui.api.navigation.NavigationAction
import com.fullrandom.fiti.core.ui.api.navigation.Navigator

@Composable
fun FitiNavigation(
    initialDestination: FitiNavKey,
    entryBuilders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>,
    navigator: Navigator,
) {
    val backStack = rememberNavBackStack(initialDestination)
    val entryProvider: (NavKey) -> NavEntry<NavKey> = entryProvider {
        entryBuilders.forEach { builder -> this.builder() }
    }

    LaunchedEffect(Unit) {
        for (navigationAction in navigator.navigationChannel) {
            when (navigationAction) {
                NavigationAction.Back -> backStack.removeLastOrNull()
                is NavigationAction.Navigate -> backStack.add(navigationAction.navigationKey)
            }
        }
    }

    NavDisplay(
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider
    )
}