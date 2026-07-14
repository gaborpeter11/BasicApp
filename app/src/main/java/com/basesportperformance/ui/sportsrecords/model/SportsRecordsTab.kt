package com.basesportperformance.ui.sportsrecords.model

import androidx.annotation.StringRes
import com.basesportperformance.R

@Suppress("unused")
enum class SportsRecordsTab(@StringRes val titleRes: Int) {
    All(R.string.sports_records_tab_all),
    Local(R.string.sports_records_tab_local),
    Remote(R.string.sports_records_tab_remote)
}


