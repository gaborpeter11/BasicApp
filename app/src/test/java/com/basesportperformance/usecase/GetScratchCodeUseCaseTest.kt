package com.basesportperformance.usecase


import com.basesportperformance.domain.usecase.GetScratchCodeUseCase
import com.basesportperformance.utils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.UUID
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetScratchCodeUseCaseTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = GetScratchCodeUseCase()

    @Test
    fun `returns valid UUID after delay`() = runTest {
        val result = useCase()

        assertNotNull(result)
        assertTrue(result.isNotEmpty())
        UUID.fromString(result)
    }
}