package com.basesportperformance.ui.recorddetail.model

import androidx.compose.runtime.Immutable
import com.basesportperformance.domain.model.SportsRecordSource

@Immutable
sealed interface RecordDetailUiState {

    @Immutable
    data object Loading : RecordDetailUiState

    @Immutable
    data object NotFound : RecordDetailUiState

    @Immutable
    data class Success(
        val type: String,
        val time: String,
        val location: String,
        val source: SportsRecordSource
    ) : RecordDetailUiState
}
