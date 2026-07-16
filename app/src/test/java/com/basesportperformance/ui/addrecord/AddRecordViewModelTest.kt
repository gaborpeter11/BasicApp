package com.basesportperformance.ui.addrecord

import app.cash.turbine.test
import com.basesportperformance.domain.model.SaveSportsRecordParams
import com.basesportperformance.domain.usecase.SaveSportsRecordUseCase
import com.basesportperformance.ui.addrecord.model.SportType
import com.basesportperformance.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AddRecordViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val saveSportsRecordUseCase: SaveSportsRecordUseCase = mockk()
    private val viewModel = AddRecordViewModel(saveSportsRecordUseCase)

    @Test
    fun `initial state has add record enabled`() {
        assertTrue(viewModel.uiState.value.isAddRecordEnabled)
    }

    @Test
    fun `blank location disables add record`() {
        viewModel.onLocationChanged("")

        assertFalse(viewModel.uiState.value.isAddRecordEnabled)
    }

    @Test
    fun `blank duration disables add record`() {
        viewModel.onDurationChanged("")

        assertFalse(viewModel.uiState.value.isAddRecordEnabled)
    }

    @Test
    fun `non-blank location and duration enable add record`() {
        viewModel.onLocationChanged("")
        viewModel.onDurationChanged("")

        viewModel.onLocationChanged("River Row Club")
        viewModel.onDurationChanged("00:27:51")

        assertTrue(viewModel.uiState.value.isAddRecordEnabled)
    }

    @Test
    fun `onSportSelected updates selected sport`() {
        viewModel.onSportSelected(SportType.SWIMMING)

        assertEquals(SportType.SWIMMING, viewModel.uiState.value.selectedSport)
    }

    @Test
    fun `onUseCurrentLocation sets location placeholder`() {
        viewModel.onUseCurrentLocation()

        assertEquals("Current location", viewModel.uiState.value.location)
    }

    @Test
    fun `onStoreLocallyChanged updates storage flag`() {
        viewModel.onStoreLocallyChanged(false)

        assertFalse(viewModel.uiState.value.storeLocally)
    }

    @Test
    fun `addRecord saves current state and navigates away on success`() = runTest {
        coEvery { saveSportsRecordUseCase(any()) } returns Unit

        viewModel.onSportSelected(SportType.CYCLING)
        viewModel.onLocationChanged("North Ridge")
        viewModel.onDurationChanged("01:12:03")
        viewModel.onStoreLocallyChanged(false)

        viewModel.navigationEvents.test {
            viewModel.addRecord()

            awaitItem()

            coVerify {
                saveSportsRecordUseCase(
                    SaveSportsRecordParams(
                        sport = "Cycling",
                        duration = "01:12:03",
                        location = "North Ridge",
                        storeLocally = false
                    )
                )
            }
        }
    }

    @Test
    fun `addRecord emits error event when use case fails`() = runTest {
        coEvery { saveSportsRecordUseCase(any()) } throws IllegalStateException("save failed")

        viewModel.errorEvents.test {
            viewModel.addRecord()

            awaitItem()
        }
    }
}