package com.basesportperformance.ui.sportsrecords

import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import com.basesportperformance.domain.usecase.GetSportsRecordsUseCase
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsAction
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsTab
import com.basesportperformance.ui.sportsrecords.model.SportsRecordsUiState
import com.basesportperformance.utils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class SportsRecordsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSportsRecordsUseCase: GetSportsRecordsUseCase = mockk()

    private val localRecord = SportsRecordDto(
        id = "1",
        location = "City Park Track",
        time = "00:42:18",
        type = "Running",
        source = SportsRecordSource.Local
    )
    private val remoteRecord = SportsRecordDto(
        id = "2",
        location = "Aquatic Center",
        time = "00:18:44",
        type = "Swimming",
        source = SportsRecordSource.Remote
    )

    private fun viewModel() = SportsRecordsViewModel(getSportsRecordsUseCase)

    @Test
    fun `publishes success state with mapped records`() = runTest {
        every { getSportsRecordsUseCase() } returns flowOf(listOf(localRecord, remoteRecord))

        val state = viewModel().uiState.value

        val success = assertIs<SportsRecordsUiState.Success>(state)
        assertEquals(listOf("1", "2"), success.records.map { it.id })
    }

    @Test
    fun `publishes empty state when there are no records`() = runTest {
        every { getSportsRecordsUseCase() } returns flowOf(emptyList())

        val state = viewModel().uiState.value

        assertIs<SportsRecordsUiState.Empty>(state)
    }

    @Test
    fun `publishes error state when the records flow fails`() = runTest {
        every { getSportsRecordsUseCase() } returns flow { throw IllegalStateException("offline") }

        val state = viewModel().uiState.value

        val error = assertIs<SportsRecordsUiState.Error>(state)
        assertEquals("offline", error.message)
    }

    @Test
    fun `selecting the local tab filters out remote records`() = runTest {
        every { getSportsRecordsUseCase() } returns flowOf(listOf(localRecord, remoteRecord))
        val viewModel = viewModel()

        viewModel.onAction(SportsRecordsAction.TabSelected(SportsRecordsTab.Local))

        val success = assertIs<SportsRecordsUiState.Success>(viewModel.uiState.value)
        assertEquals(listOf(localRecord.id), success.records.map { it.id })
        assertEquals(SportsRecordsTab.Local, success.selectedTab)
    }

    @Test
    fun `selecting the remote tab filters out local records`() = runTest {
        every { getSportsRecordsUseCase() } returns flowOf(listOf(localRecord, remoteRecord))
        val viewModel = viewModel()

        viewModel.onAction(SportsRecordsAction.TabSelected(SportsRecordsTab.Remote))

        val success = assertIs<SportsRecordsUiState.Success>(viewModel.uiState.value)
        assertEquals(listOf(remoteRecord.id), success.records.map { it.id })
    }

    @Test
    fun `retry reloads records from the use case`() = runTest {
        every { getSportsRecordsUseCase() } returns flowOf(listOf(localRecord))
        val viewModel = viewModel()

        viewModel.onAction(SportsRecordsAction.Retry)

        verify(exactly = 2) { getSportsRecordsUseCase() }
    }
}