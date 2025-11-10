package com.example.basicapp.ui.main

import androidx.lifecycle.ViewModel
import com.example.basicapp.data.ScratchCardRepository
import com.example.basicapp.domain.model.ScratchCardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ScratchCardRepository
) : ViewModel() {
    val cardState: StateFlow<ScratchCardState> = repo.state
}