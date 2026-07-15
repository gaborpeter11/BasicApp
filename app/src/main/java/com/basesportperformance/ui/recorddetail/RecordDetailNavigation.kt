package com.basesportperformance.ui.recorddetail

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basesportperformance.navigation.RecordDetail

internal fun NavGraphBuilder.recordDetailScreen(
    onClose: () -> Unit
) {
    composable<RecordDetail> {
        RecordDetailRoute(
            onClose = onClose
        )
    }
}
