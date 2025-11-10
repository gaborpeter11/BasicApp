package com.example.basicapp.vm

import com.example.basicapp.data.ScratchCardRepository
import com.example.basicapp.domain.model.ScratchCardState
import com.example.basicapp.domain.usecase.ActivateUseCase
import com.example.basicapp.ui.activate.ActivateViewModel
import com.example.basicapp.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActivateViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: ActivateUseCase
    private lateinit var repo: ScratchCardRepository
    private lateinit var viewModel: ActivateViewModel
    private lateinit var appScope: CoroutineScope

    @Before
    fun setup() {
        useCase = mockk()

        val fakeStateFlow = MutableStateFlow(ScratchCardState.Unscratched)
        repo = mockk(relaxed = true) {
            every { state } returns fakeStateFlow
        }

        appScope = CoroutineScope(mainDispatcherRule.testDispatcher)
        viewModel = ActivateViewModel(useCase, repo, appScope)
    }

    @Test
    fun `sets activated when usecase returns true`() = runTest {
        coEvery { useCase.invoke(any()) } returns true

        viewModel.activateInBackground("code123")
        advanceUntilIdle()

        coVerify { repo.setActivated() }
    }

    @Test
    fun `sets error when usecase returns false`() = runTest {
        coEvery { useCase.invoke(any()) } returns false

        viewModel.activateInBackground("code123")
        advanceUntilIdle()

        coVerify { repo.setError(match { it.contains("rejected") }) }
    }

    @Test
    fun `sets error when exception thrown`() = runTest {
        coEvery { useCase.invoke(any()) } throws RuntimeException("Network fail")

        viewModel.activateInBackground("code123")
        advanceUntilIdle()

        coVerify { repo.setError(match { it.contains("Activation failed") }) }
    }
}
