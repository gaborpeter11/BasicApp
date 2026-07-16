package com.basesportperformance.data.remote

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthManager @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    private val signInMutex = Mutex()

    suspend fun currentUserId(): String {
        firebaseAuth.currentUser?.let { return it.uid }

        return signInMutex.withLock {
            firebaseAuth.currentUser?.let { return@withLock it.uid }

            val result = firebaseAuth.signInAnonymously().await()
            result.user?.uid
                ?: error("Firebase anonymous sign-in did not return a user")
        }
    }
}
