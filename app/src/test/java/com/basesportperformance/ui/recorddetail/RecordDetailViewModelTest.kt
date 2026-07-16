package com.basesportperformance.ui.recorddetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import com.basesportperformance.domain.usecase.GetSportsRecordByIdUseCase
import com.basesportperformance.ui.recorddetail.model.RecordDetailUiState
import com.basesportperformance.utils.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

/**
 * [RecordDetailViewModel] decodes navigation args via [SavedStateHandle.toRoute], which internally
 * touches the real android.os.Bundle API. That's unavailable on a pure JVM unit test, so this
 * class runs under Robolectric instead.
 */
@RunWith(RobolectricTestRunner::class)
class RecordDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getSportsRecordByIdUseCase: GetSportsRecordByIdUseCase = mockk()

    private fun viewModel(id: String = "1", source: SportsRecordSource = SportsRecordSource.Local) =
        RecordDetailViewModel(
            savedStateHandle = SavedStateHandle(mapOf("id" to id, "source" to source)),
            getSportsRecordByIdUseCase = getSportsRecordByIdUseCase
        )

    @Test
    fun `emits success state when record is found`() = runTest {
        val record = SportsRecordDto(
            id = "1",
            location = "City Park Track",
            time = "00:42:18",
            type = "Running",
            source = SportsRecordSource.Local
        )
        every { getSportsRecordByIdUseCase("1", SportsRecordSource.Local) } returns flowOf(record)

        viewModel(id = "1", source = SportsRecordSource.Local).uiState.test {
            assertEquals(
                RecordDetailUiState.Success(
                    type = "Running",
                    time = "00:42:18",
                    location = "City Park Track",
                    source = SportsRecordSource.Local
                ),
                awaitItem()
            )
        }
    }

    @Test
    fun `emits not found state when record is null`() = runTest {
        every { getSportsRecordByIdUseCase("missing", SportsRecordSource.Remote) } returns flowOf(null)

        viewModel(id = "missing", source = SportsRecordSource.Remote).uiState.test {
            assertEquals(RecordDetailUiState.NotFound, awaitItem())
        }
    }

    @Test
    fun `emits loading state before the record flow produces a value`() = runTest {
        val neverEmits = MutableSharedFlow<SportsRecordDto?>()
        every { getSportsRecordByIdUseCase("1", SportsRecordSource.Local) } returns neverEmits

        viewModel(id = "1", source = SportsRecordSource.Local).uiState.test {
            assertEquals(RecordDetailUiState.Loading, awaitItem())
        }
    }
}