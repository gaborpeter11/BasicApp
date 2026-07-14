package com.basesportperformance.ui.sportsrecords.model

import com.basesportperformance.domain.model.SportsRecordSource

internal val previewSportsRecords = listOf(
    SportsRecord(
        id = 1,
        name = "Morning Run",
        time = "00:42:18",
        type = "Running",
        source = SportsRecordSource.Local
    ),
    SportsRecord(
        id = 2,
        name = "Pool Sprint",
        time = "00:18:44",
        type = "Swimming",
        source = SportsRecordSource.Remote
    ),
    SportsRecord(
        id = 3,
        name = "Hill Climb",
        time = "01:12:03",
        type = "Cycling",
        source = SportsRecordSource.Local
    ),
    SportsRecord(
        id = 4,
        name = "Evening Recovery",
        time = "00:27:51",
        type = "Rowing",
        source = SportsRecordSource.Remote
    )
)

