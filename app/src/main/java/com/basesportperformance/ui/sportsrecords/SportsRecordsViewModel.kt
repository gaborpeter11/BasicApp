package com.basesportperformance.ui.sportsrecords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basesportperformance.domain.model.SportsRecordSource
import com.basesportperformance.domain.usecase.GetSportsRecordsUseCase
import com.basesportperformance.ui.sportsrecords.model.SportsRecord
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsTab
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsUiState
import com.basesportperformance.ui.sportsrecords.mapper.toUiModel
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsRecordsViewModel @Inject constructor(
    private val getSportsRecordsUseCase: GetSportsRecordsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SportsRecordsUiState>(
        SportsRecordsUiState.Loading()
    )
    val uiState: StateFlow<SportsRecordsUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null
    private var cachedRecords: List<SportsRecord> = emptyList()

    init {
        loadSportsRecords()
    }

    fun onAction(action: SportsRecordsAction) {
        when (action) {
            SportsRecordsAction.AddRecord -> Unit
            SportsRecordsAction.Retry -> retry()
            is SportsRecordsAction.TabSelected -> onTabSelected(action.tab)
            is SportsRecordsAction.RecordClicked -> Unit
        }
    }

    private fun retry() {
        loadSportsRecords(
            forceLoadingState = cachedRecords.isEmpty()
        )
    }

    private fun onTabSelected(tab: SportsRecordsTab) {
        if (currentTab == tab) return

        updateState { state ->
            when (state) {
                is SportsRecordsUiState.Loading ->
                    state.copy(selectedTab = tab)

                is SportsRecordsUiState.Empty ->
                    state.copy(selectedTab = tab)

                is SportsRecordsUiState.Error ->
                    state.copy(selectedTab = tab)

                is SportsRecordsUiState.Success ->
                    state.copy(selectedTab = tab)
            }
        }

        if (cachedRecords.isNotEmpty()) {
            publishContentState()
        }
    }

    private fun loadSportsRecords(
        forceLoadingState: Boolean = true
    ) {
        if (loadJob?.isActive == true) return

        if (forceLoadingState) {
            _uiState.value =
                SportsRecordsUiState.Loading(currentTab)
        }

        loadJob = viewModelScope.launch {
            getSportsRecordsUseCase()
                .catch { throwable ->
                    if (cachedRecords.isNotEmpty()) {
                        publishContentState()
                    } else {
                        _uiState.value =
                            SportsRecordsUiState.Error(
                                message = throwable.message
                                    ?: "Unable to load sports records.",
                                selectedTab = currentTab
                            )
                    }
                }
                .collect { records ->
                    cachedRecords =
                        records.map { it.toUiModel() }

                    publishContentState()
                }
        }
    }

    private fun publishContentState() {

        val filtered =
            filterRecords(
                records = cachedRecords,
                tab = currentTab
            )

        _uiState.value =
            if (filtered.isEmpty()) {
                SportsRecordsUiState.Empty(currentTab)
            } else {
                SportsRecordsUiState.Success(
                    records = filtered,
                    selectedTab = currentTab
                )
            }
    }

    private fun filterRecords(
        records: List<SportsRecord>,
        tab: SportsRecordsTab
    ): List<SportsRecord> =
        when (tab) {
            SportsRecordsTab.All -> records
            SportsRecordsTab.Local ->
                records.filter { it.source == SportsRecordSource.Local }

            SportsRecordsTab.Remote ->
                records.filter { it.source == SportsRecordSource.Remote }
        }

    private inline fun updateState(
        transform: (SportsRecordsUiState) -> SportsRecordsUiState
    ) {
        _uiState.value = transform(_uiState.value)
    }

    private val currentTab: SportsRecordsTab
        get() = _uiState.value.selectedTab
}