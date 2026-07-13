package com.basesportperformance.ui.scratch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basesportperformance.data.ScratchCardRepository
import com.basesportperformance.domain.model.ScratchCardState
import com.basesportperformance.domain.model.ScratchState
import com.basesportperformance.domain.usecase.GetScratchCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScratchViewModel @Inject constructor(
    private val getScratchCodeUseCase: GetScratchCodeUseCase,
    private val repo: ScratchCardRepository
) : ViewModel() {

    val cardState: StateFlow<ScratchCardState> = repo.state

    private val _scratchScreenState = MutableStateFlow(ScratchState())
    val scratchScreenState = _scratchScreenState.asStateFlow()

    private var currentJob: Job? = null

    fun scratch() {
        if (currentJob?.isActive == true) return

        currentJob = viewModelScope.launch {
            _scratchScreenState.update { it.copy(loading = true) }

            runCatching {
                getScratchCodeUseCase()
            }.onSuccess { code ->
                repo.setScratched(code)
            }.onFailure { e ->
                repo.setError(e.message ?: "Scratching failed")
            }.also {
                _scratchScreenState.update { it.copy(loading = false) }
            }
        }
    }
}

