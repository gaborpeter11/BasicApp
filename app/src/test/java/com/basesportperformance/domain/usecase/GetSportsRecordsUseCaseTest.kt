package com.basesportperformance.domain.usecase

import app.cash.turbine.test
import com.basesportperformance.data.SportsRecordsRepository
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class GetSportsRecordsUseCaseTest {

    private val repository: SportsRecordsRepository = mockk()
    private val useCase = GetSportsRecordsUseCase(repository)

    @Test
    fun `delegates to repository and emits its records`() = runTest {
        val records = listOf(
            SportsRecordDto(
                id = "1",
                location = "City Park Track",
                time = "00:42:18",
                type = "Running",
                source = SportsRecordSource.Local
            ),
            SportsRecordDto(
                id = "2",
                location = "Aquatic Center",
                time = "00:18:44",
                type = "Swimming",
                source = SportsRecordSource.Remote
            )
        )
        every { repository.observeSportsRecords() } returns flowOf(records)

        useCase().test {
            assertEquals(records, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeSportsRecords() }
    }
}