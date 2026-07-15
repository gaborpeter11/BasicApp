package com.basesportperformance.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.basesportperformance.domain.model.SportsRecordDto
import com.basesportperformance.domain.model.SportsRecordSource

@Entity(tableName = "sports_records")
data class SportsRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val location: String,
    val time: String,
    val type: String,
    val source: SportsRecordSource
)

fun SportsRecordEntity.toDomainModel(): SportsRecordDto = SportsRecordDto(
    id = id,
    name = name,
    location = location,
    time = time,
    type = type,
    source = source
)

fun List<SportsRecordEntity>.toDomain(): List<SportsRecordDto> =
    map { it.toDomainModel() }

fun SportsRecordDto.toEntity(): SportsRecordEntity = SportsRecordEntity(
    id = id,
    name = name,
    location = location,
    time = time,
    type = type,
    source = source
)


