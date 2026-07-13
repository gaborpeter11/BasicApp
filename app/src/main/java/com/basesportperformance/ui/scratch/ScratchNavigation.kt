package com.basesportperformance.ui.scratch

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.Scratch

internal fun NavGraphBuilder.scratchScreen(
    onClose: () -> Unit
) {
    composable<Scratch> {

        ScratchRoute(
            onClose = onClose
        )
    }
}
