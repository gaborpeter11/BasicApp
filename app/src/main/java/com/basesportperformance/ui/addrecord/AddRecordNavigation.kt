package com.basesportperformance.ui.addrecord

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.AddRecord

internal fun NavGraphBuilder.addRecordScreen(
    onClose: () -> Unit
) {
    composable<AddRecord> {
        AddRecordRoute(
            onClose = onClose
        )
    }
}

