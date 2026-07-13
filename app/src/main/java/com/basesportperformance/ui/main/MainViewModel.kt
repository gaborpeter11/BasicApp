package com.basesportperformance.ui.main

import androidx.lifecycle.ViewModel
import com.basesportperformance.data.ScratchCardRepository
import com.basesportperformance.domain.model.ScratchCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ScratchCardRepository
) : ViewModel() {
    val cardState: StateFlow<ScratchCardState> = repo.state
}