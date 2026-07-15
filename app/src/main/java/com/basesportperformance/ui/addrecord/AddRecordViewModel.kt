package com.basesportperformance.ui.addrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basesportperformance.domain.usecase.SaveSportsRecordUseCase
import com.basesportperformance.ui.addrecord.mapper.toSaveSportsRecordParams
import com.basesportperformance.ui.addrecord.model.AddRecordUiState
import com.basesportperformance.ui.addrecord.model.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor(
    private val saveSportsRecordUseCase: SaveSportsRecordUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddRecordUiState())
    val uiState = _uiState.asStateFlow()
    private val _navigationEvents = MutableSharedFlow<Unit>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun addRecord() {
        val currentState = _uiState.value

        viewModelScope.launch {
            saveSportsRecordUseCase(currentState.toSaveSportsRecordParams())
            _navigationEvents.emit(Unit)
        }
    }

    fun onSportSelected(sport: SportType) {
        updateState { copy(selectedSport = sport) }
    }

    fun onLocationChanged(value: String) {
        updateState { copy(location = value) }
    }

    fun onUseCurrentLocation() {
        updateState { copy(location = LOCATION_PLACEHOLDER) }
    }

    fun onDurationChanged(value: String) {
        updateState {
            copy(duration = value)
        }
    }

    fun onStoreLocallyChanged(value: Boolean) {
        updateState { copy(storeLocally = value) }
    }

    private fun updateState(
        update: AddRecordUiState.() -> AddRecordUiState
    ) {
        _uiState.update { currentState ->
            val updatedState = currentState.update()

            updatedState.copy(
                isAddRecordEnabled = validate(updatedState)
            )
        }
    }

    private fun validate(state: AddRecordUiState): Boolean {
        return state.location.isNotBlank() &&
                state.duration.isNotBlank()
    }

    private companion object {
        const val LOCATION_PLACEHOLDER = "Current location"
    }
}

