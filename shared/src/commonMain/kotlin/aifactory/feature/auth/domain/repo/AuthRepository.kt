package aifactory.feature.auth.domain.repo
interface AuthRepository {
    suspend fun signInWithGoogle(): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun currentUserId(): String?
}