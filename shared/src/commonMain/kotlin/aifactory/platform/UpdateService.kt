package aifactory.platform

interface UpdateService {
    suspend fun check(): UpdateInfo?
}

data class UpdateInfo(val version: String, val url: String, val notes: String? = null)
