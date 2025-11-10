package com.example.basicapp.ui.activate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.basicapp.navigation.Activate


internal fun NavGraphBuilder.activateScreen(
    onClose: () -> Unit
) {
    composable<Activate> {

        ActivateRoute(
            onClose = onClose
        )
    }
}
