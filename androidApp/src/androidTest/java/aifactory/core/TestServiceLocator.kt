package aifactory.core

import aifactory.data.AiRepository
import aifactory.data.local.FeedStore
import aifactory.data.local.KeyValueStore
import aifactory.data.mock.InMemoryAiRepository
import android.content.Context

/**
 * A test-specific service locator to inject fake or mock dependencies during UI tests.
 */
object TestServiceLocator {

    fun setupTestEnvironment(context: Context) {
        // Create in-memory fakes for the stores
        val fakeKeyValueStore = InMemoryKeyValueStore() // Assuming this class exists
        val fakeFeedStore = InMemoryFeedStore()     // Assuming this class exists

        // Create a repository instance with the fakes
        val testRepository = InMemoryAiRepository(
            store = fakeKeyValueStore,
            feedStore = fakeFeedStore
        )

        // Swap the real repository with our test instance
        ServiceLocator.swapRepository(testRepository)
    }

    fun cleanupTestEnvironment() {
        // Reset the service locator to its original state if needed
        // This might involve clearing the stores or swapping back the original repo
    }
}

// These would be fake, in-memory implementations for testing
class InMemoryKeyValueStore : KeyValueStore { 
    private val map = mutableMapOf<String, String>()
    override suspend fun read(key: String): String? = map[key]
    override suspend fun write(key: String, value: String): Boolean {
        map[key] = value
        return true
    }
    override suspend fun clear(): Boolean {
        map.clear()
        return true
    }
}

class InMemoryFeedStore : FeedStore { 
    private var snapshot: aifactory.data.local.FeedSnapshotV1? = null
    override suspend fun read(): aifactory.data.local.FeedSnapshotV1? = snapshot
    override suspend fun write(snapshot: aifactory.data.local.FeedSnapshotV1): Boolean {
        this.snapshot = snapshot
        return true
    }
    override suspend fun clear(): Boolean {
        snapshot = null
        return true
    }
}
