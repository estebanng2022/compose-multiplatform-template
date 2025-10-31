package aifactory.core

import aifactory.data.AiRepository
import aifactory.data.local.FeedStore
import aifactory.data.local.FileFeedStore
import aifactory.data.local.FileKeyValueStore
import aifactory.data.local.KeyValueStore
import aifactory.data.mock.InMemoryAiRepository
import android.content.Context

object ServiceLocator {
    fun repository(): AiRepository {
        return repo ?: throw IllegalStateException("Repository not initialized; call repository(context) first")
    }

    @Volatile
    private var repo: AiRepository? = null

    @Volatile
    private var kv: KeyValueStore? = null

    @Volatile
    private var feedStore: FeedStore? = null

    fun repository(context: Context): AiRepository {
        return repo ?: synchronized(this) {
            repo ?: InMemoryAiRepository(
                store = keyValueStore(context),
                feedStore = feedStore(context)
            ).also { repo = it }
        }
    }

    fun keyValueStore(context: Context): KeyValueStore {
        return kv ?: synchronized(this) {
            kv ?: FileKeyValueStore(context).also { kv = it }
        }
    }

    fun feedStore(context: Context): FeedStore {
        return feedStore ?: synchronized(this) {
            feedStore ?: FileFeedStore(context).also { feedStore = it }
        }
    }

    fun swapRepository(testRepo: AiRepository) {
        repo = testRepo
    }
}


