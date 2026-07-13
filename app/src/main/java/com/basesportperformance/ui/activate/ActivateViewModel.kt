package com.basesportperformance.ui.activate

import androidx.lifecycle.ViewModel
import com.basesportperformance.data.ScratchCardRepository
import com.basesportperformance.di.ApplicationScope
import com.basesportperformance.domain.model.ScratchCardState
import com.basesportperformance.domain.usecase.ActivateUseCase
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
