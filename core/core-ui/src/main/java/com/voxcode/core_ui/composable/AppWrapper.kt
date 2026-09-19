package com.voxcode.core_ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppWrapper(
    content: @Composable () -> Unit
) {
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateBottomPadding()
    MaterialTheme {
        Scaffold(
            topBar = {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(
                                statusBarHeight
                            ).background(
                                Color.Black
                            )
                )
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }
}
