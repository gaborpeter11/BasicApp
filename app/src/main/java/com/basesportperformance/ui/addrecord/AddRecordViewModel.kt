package com.basesportperformance.ui.addrecord

import androidx.lifecycle.ViewModel
import com.basesportperformance.ui.addrecord.model.AddRecordUiState
import com.basesportperformance.ui.addrecord.model.SportType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddRecordViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AddRecordUiState())
    val uiState = _uiState.asStateFlow()

    fun addRecord() = Unit  //TODO: close screen on success/failure

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