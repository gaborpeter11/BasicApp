package com.basesportperformance.domain.usecase

import com.basesportperformance.data.SportsRecordsRepository
import com.basesportperformance.domain.model.SaveSportsRecordParams
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertFailsWith

class SaveSportsRecordUseCaseTest {

    private val repository: SportsRecordsRepository = mockk(relaxed = true)
    private val useCase = SaveSportsRecordUseCase(repository)

    @Test
    fun `saves record as local when storeLocally is true`() = runTest {
        val params = SaveSportsRecordParams(
            sport = "Running",
            duration = "00:30:00",
            location = "City Park",
            storeLocally = true
        )

        useCase(params)

        coVerify {
            repository.saveSportsRecord(
                SportsRecordDto(
                    id = "",
                    location = "City Park",
                    time = "00:30:00",
                    type = "Running",
                    source = SportsRecordSource.Local
                )
            )
        }
    }

    @Test
    fun `saves record as remote when storeLocally is false`() = runTest {
        val params = SaveSportsRecordParams(
            sport = "Swimming",
            duration = "00:18:44",
            location = "Aquatic Center",
            storeLocally = false
        )

        useCase(params)

        coVerify {
            repository.saveSportsRecord(
                SportsRecordDto(
                    id = "",
                    location = "Aquatic Center",
                    time = "00:18:44",
                    type = "Swimming",
                    source = SportsRecordSource.Remote
                )
            )
        }
    }

    @Test
    fun `propagates failure from repository`() = runTest {
        val params = SaveSportsRecordParams(
            sport = "Running",
            duration = "00:30:00",
            location = "City Park",
            storeLocally = true
        )
        coEvery { repository.saveSportsRecord(any()) } throws IllegalStateException("write failed")

        assertFailsWith<IllegalStateException> { useCase(params) }
    }
}