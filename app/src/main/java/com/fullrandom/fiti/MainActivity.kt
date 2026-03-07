package com.fullrandom.fiti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.fullrandom.calories.ui.api.CaloriesUiNavKeys
import com.fullrandom.fiti.core.ui.api.navigation.FitiNavKey
import com.fullrandom.fiti.core.ui.api.navigation.NavigationAction
import com.fullrandom.fiti.core.ui.api.navigation.Navigator
import com.fullrandom.fiti.core.ui.navigation.FitiNavigation
import com.fullrandom.fiti.ui.theme.FitiTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var entryBuilders: Set<@JvmSuppressWildcards EntryProviderScope<NavKey>.() -> Unit>

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FitiAppCompose()
                }
            }
        }
    }
}


@Composable
private fun MainActivity.FitiAppCompose() {
    FitiNavigation(
        initialDestination = CaloriesUiNavKeys.SummaryScreenNavKey,
        entryBuilders = entryBuilders,
        navigator = navigator
    )
}
