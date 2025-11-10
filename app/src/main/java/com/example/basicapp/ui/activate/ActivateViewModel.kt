package com.example.basicapp.ui.activate

import androidx.lifecycle.ViewModel
import com.example.basicapp.data.ScratchCardRepository
import com.example.basicapp.di.ApplicationScope
import com.example.basicapp.domain.model.ScratchCardState
import com.example.basicapp.domain.usecase.ActivateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivateViewModel @Inject constructor(
    private val activateUseCase: ActivateUseCase,
    private val repo: ScratchCardRepository,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    val cardState: StateFlow<ScratchCardState> = repo.state

    fun activateInBackground(code: String) {
        applicationScope.launch {
            runCatching {
                activateUseCase.invoke(code)
            }.onSuccess { success ->
                if (success) {
                    repo.setActivated()
                } else {
                    repo.setError("Activation rejected by remote service.")
                }
            }.onFailure { t ->
                repo.setError("Activation failed: ${t.message ?: "unknown"}")
            }
        }
    }
}
