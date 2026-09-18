package com.voxcode.core_ui.composable

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.voxcode.core_ui.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter

val LocalNavController = staticCompositionLocalOf<NavController> { error("No AppState provided") }

// Screen composable is basis for every screen in the app defined by viewmodel
// It contain states which is stored from child viewmodel into base view model
@SuppressLint("RestrictedApi")
@Composable
fun <State : Any> Screen(
    viewModel: BaseViewModel<State>,
    content: @Composable (State) -> Unit
) {
    val viewState by viewModel.state.collectAsState()
    val context = LocalContext.current
    val navController = LocalNavController.current

    LaunchedEffect(context) {
        navController.currentBackStackEntryFlow
            .filter {
                it.maxLifecycle == Lifecycle.State.RESUMED
            }
            .collect()
    }
    content(viewState)
}