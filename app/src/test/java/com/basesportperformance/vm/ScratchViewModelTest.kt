package com.basesportperformance.vm

import app.cash.turbine.test
import com.basesportperformance.data.ScratchCardRepository
import com.basesportperformance.domain.usecase.GetScratchCodeUseCase
import com.basesportperformance.ui.scratch.ScratchViewModel
import com.basesportperformance.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ScratchViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: GetScratchCodeUseCase
    private lateinit var repo: ScratchCardRepository
    private lateinit var viewModel: ScratchViewModel

    @Before
    fun setup() {
        useCase = mockk()
        repo = mockk(relaxed = true)
        viewModel = ScratchViewModel(useCase, repo)
    }

    @Test
    fun `sets scratched when successful`() = runTest {
        coEvery { useCase.invoke() } returns "CODE123"

        viewModel.scratch()
        advanceUntilIdle()

        coVerify { repo.setScratched("CODE123") }
    }

    @Test
    fun `sets error when exception thrown`() = runTest {
        coEvery { useCase.invoke() } throws RuntimeException("Scratch fail")

        viewModel.scratch()
        advanceUntilIdle()

        coVerify { repo.setError(match { it.contains("Scratch fail") }) }
    }

    @Test
    fun `updates loading true then false`() = runTest {
        coEvery { useCase.invoke() } returns "CODE123"

        viewModel.scratchScreenState.test {
            assertFalse(awaitItem().loading)
            viewModel.scratch()
            advanceUntilIdle()
            assertTrue(awaitItem().loading)
            assertFalse(awaitItem().loading)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
