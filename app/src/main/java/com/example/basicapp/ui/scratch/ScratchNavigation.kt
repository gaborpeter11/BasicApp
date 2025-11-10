package com.example.basicapp.ui.scratch

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basicapp.navigation.Scratch

internal fun NavGraphBuilder.scratchScreen(
    onClose: () -> Unit
) {
    composable<Scratch> {

        ScratchRoute(
            onClose = onClose
        )
    }
}
