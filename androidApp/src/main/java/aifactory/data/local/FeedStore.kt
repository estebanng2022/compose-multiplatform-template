package aifactory.data.local

interface FeedStore {
    suspend fun read(): FeedSnapshotV1?
    suspend fun write(snapshot: FeedSnapshotV1): Boolean
    suspend fun clear(): Boolean
}
