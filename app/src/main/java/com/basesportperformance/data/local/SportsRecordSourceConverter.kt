package com.basesportperformance.data.local

import androidx.room.TypeConverter
import com.basesportperformance.domain.model.SportsRecordSource

class SportsRecordSourceConverter {

    @TypeConverter
    fun fromSource(source: SportsRecordSource): String = source.name

    @TypeConverter
    fun toSource(value: String): SportsRecordSource = SportsRecordSource.valueOf(value)
}

