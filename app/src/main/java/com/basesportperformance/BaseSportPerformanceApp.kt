package com.basesportperformance

import android.app.Application
import com.basesportperformance.domain.usecase.SeedSportsRecordsUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class BaseSportPerformanceApp : Application() {

	@Inject
	lateinit var seedSportsRecordsUseCase: SeedSportsRecordsUseCase

	@Inject
	@com.basesportperformance.di.ApplicationScope
	lateinit var applicationScope: CoroutineScope

	override fun onCreate() {
		super.onCreate()

		applicationScope.launch {
			seedSportsRecordsUseCase()
		}
	}
}