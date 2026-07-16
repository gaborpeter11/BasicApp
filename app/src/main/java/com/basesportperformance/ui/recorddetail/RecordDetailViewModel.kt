package com.basesportperformance.ui.recorddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.basesportperformance.domain.usecase.GetSportsRecordByIdUseCase
import com.basesportperformance.navigation.RecordDetail
import com.basesportperformance.ui.recorddetail.mapper.toDetailUiState
import com.basesportperformance.ui.recorddetail.model.RecordDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RecordDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSportsRecordByIdUseCase: GetSportsRecordByIdUseCase
) : ViewModel() {

    private val route = savedStateHandle.toRoute<RecordDetail>()

    val uiState: StateFlow<RecordDetailUiState> =
        getSportsRecordByIdUseCase(route.id, route.source)
            .map { record -> record?.toDetailUiState() ?: RecordDetailUiState.NotFound }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
                initialValue = RecordDetailUiState.Loading
            )

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
