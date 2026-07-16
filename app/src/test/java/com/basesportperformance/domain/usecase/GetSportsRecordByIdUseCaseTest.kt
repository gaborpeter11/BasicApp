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

class GetSportsRecordByIdUseCaseTest {

    private val repository: SportsRecordsRepository = mockk()
    private val useCase = GetSportsRecordByIdUseCase(repository)

    @Test
    fun `delegates to repository with given id and source and emits its records`() = runTest {
        val record = SportsRecordDto(
            id = "1",
            location = "City Park Track",
            time = "00:42:18",
            type = "Running",
            source = SportsRecordSource.Local
        )
        every { repository.observeSportsRecord("1", SportsRecordSource.Local) } returns flowOf(record)

        useCase("1", SportsRecordSource.Local).test {
            assertEquals(record, awaitItem())
            awaitComplete()
        }

        verify(exactly = 1) { repository.observeSportsRecord("1", SportsRecordSource.Local) }
    }

    @Test
    fun `emits null when record is not found`() = runTest {
        every { repository.observeSportsRecord("missing", SportsRecordSource.Remote) } returns flowOf(null)

        useCase("missing", SportsRecordSource.Remote).test {
            assertEquals(null, awaitItem())
            awaitComplete()
        }
    }
}