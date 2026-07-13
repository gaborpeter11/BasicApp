package com.basesportperformance.usecase

import com.basesportperformance.domain.usecase.ActivateUseCase
import com.basesportperformance.data.ApiService
import com.basesportperformance.data.VersionResponse
import com.basesportperformance.utils.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith

class ActivateUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val apiService: ApiService = mockk()
    private val useCase = ActivateUseCase(apiService)

    @Test
    fun `returns true when android version greater than 277028`() = runTest {
        coEvery { apiService.getVersion(any()) } returns VersionResponse(android = 300000L)

        val result = useCase("test-code")

        assertTrue(result)
    }

    @Test
    fun `returns false when android version lower`() = runTest {
        coEvery { apiService.getVersion(any()) } returns VersionResponse(android = 100L)

        val result = useCase("test-code")

        assertFalse(result)
    }

    @Test
    fun `throws when apiService fails`() = runTest {
        coEvery { apiService.getVersion(any()) } throws RuntimeException("network error")

        assertFailsWith<RuntimeException> {
            useCase("code")
        }
    }
}
