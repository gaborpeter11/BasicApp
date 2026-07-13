package com.basesportperformance.ui.activate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.Activate


internal fun NavGraphBuilder.activateScreen(
    onClose: () -> Unit
) {
    composable<Activate> {

        ActivateRoute(
            onClose = onClose
        )
    }
}
