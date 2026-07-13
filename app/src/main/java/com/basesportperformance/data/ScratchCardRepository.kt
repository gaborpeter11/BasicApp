package com.basesportperformance.data

import com.basesportperformance.domain.model.ScratchCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScratchCardRepository @Inject constructor() {

    private val _state = MutableStateFlow<ScratchCardState>(ScratchCardState.Unscratched)
    val state: StateFlow<ScratchCardState> = _state

    fun setScratched(code: String) {
        _state.value = ScratchCardState.Scratched(code)
    }

    fun setActivated() {
        _state.value = ScratchCardState.Activated
    }

    fun setError(message: String) {
        _state.value = ScratchCardState.Error(message)
    }

    fun reset() {
        _state.value = ScratchCardState.Unscratched
    }

    fun currentState(): ScratchCardState = _state.value
}
