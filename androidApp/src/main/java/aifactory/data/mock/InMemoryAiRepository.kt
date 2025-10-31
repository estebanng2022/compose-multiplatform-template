package aifactory.data.mock

import aifactory.core.Logx
import aifactory.data.AiRepository
import aifactory.data.local.FeedSnapshotV1
import aifactory.data.local.FeedStore
import aifactory.data.local.KeyValueStore
import aifactory.ui.screens.dashboard.DashboardItem
import aifactory.ui.screens.dashboard.DashboardPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class InMemoryAiRepository(
    private val store: KeyValueStore,
    private val feedStore: FeedStore
) : AiRepository {

    // --- Settings Persistence ---
    private var cachedSettings: MutableMap<String, Boolean>? = null
    private val settingsMutex = Mutex()
    private val defaultSettings = mapOf(
        "notifications" to true,
        "autoSync" to false,
        "analytics" to false
    )

    // --- Favorites Persistence ---
    private val FAVORITES_KEY = "favorites"
    private lateinit var favoriteIds: MutableSet<String>
    private val favoritesMutex = Mutex()

    // --- Feed Persistence ---
    private var cachedPage: MutableList<DashboardItem> = mutableListOf()
    private var cachedNextCursor: String? = null
    private val feedTtlMillis: Long = 10 * 60 * 1000L // 10 min
    private var lastLoadMillis: Long = 0L
    private val feedMutex = Mutex()

    private val allDashboardItems = List(100) { i ->
        DashboardItem(
            id = "item_$i",
            title = "Item #$i",
            subtitle = "This is a descriptive subtitle for item $i",
        )
    }

    private suspend fun ensureFavoritesLoaded() = favoritesMutex.withLock {
        if (!::favoriteIds.isInitialized) {
            favoriteIds = loadFavoritesFromStore()
        }
    }

    private suspend fun ensureFeedLoaded() = feedMutex.withLock {
        if (cachedPage.isEmpty()) {
            val snap = feedStore.read()
            if (snap != null && (System.currentTimeMillis() - snap.savedAtMillis < snap.ttlMillis)) {
                cachedPage = snap.items.toMutableList()
                cachedNextCursor = snap.nextCursor
                lastLoadMillis = snap.savedAtMillis
            }
        }
    }

    @Deprecated("Use fetchDashboardPage instead")
    override suspend fun fetchDashboard(): List<Any> {
        delay(200)
        return allDashboardItems.take(5)
    }

    override suspend fun fetchSettings(): Map<String, Boolean> = settingsMutex.withLock {
        if (cachedSettings == null) {
            cachedSettings = loadSettingsFromStore()
        }
        return cachedSettings!!
    }

    override suspend fun login(user: String, pass: String): Boolean {
        delay(500)
        return user.isNotBlank() && pass.length >= 3
    }

    override suspend fun fetchDashboardPage(cursor: String?, pageSize: Int): DashboardPage = feedMutex.withLock {
        ensureFavoritesLoaded()
        ensureFeedLoaded()

        val isFirstPage = cursor == null
        val now = System.currentTimeMillis()

        if (isFirstPage && cachedPage.isNotEmpty() && (now - lastLoadMillis < feedTtlMillis)) {
            val pageItems = cachedPage.take(pageSize)
            val next = if (cachedPage.size > pageSize) cachedPage[pageSize].id else cachedNextCursor
            return DashboardPage(items = pageItems, nextCursor = next)
        }

        // Simulate network call
        delay(600)
        val startIndex = cursor?.substringAfter("item_")?.toIntOrNull() ?: if (isFirstPage) 0 else cachedPage.size
        val endIndex = (startIndex + pageSize).coerceAtMost(allDashboardItems.size)

        if (startIndex >= allDashboardItems.size) {
            return DashboardPage(items = emptyList(), nextCursor = null)
        }

        val newItems = allDashboardItems.subList(startIndex, endIndex).map {
            it.copy(isFavorite = favoriteIds.contains(it.id))
        }

        val newNextCursor = if (endIndex < allDashboardItems.size) "item_${endIndex}" else null

        if (isFirstPage) {
            cachedPage.clear()
        }
        cachedPage.addAll(newItems)
        cachedNextCursor = newNextCursor
        lastLoadMillis = now

        feedStore.write(FeedSnapshotV1(
            savedAtMillis = now,
            ttlMillis = feedTtlMillis,
            nextCursor = newNextCursor,
            items = cachedPage
        ))

        return DashboardPage(items = cachedPage.toList(), nextCursor = newNextCursor)
    }

    override suspend fun setFavorite(id: String, value: Boolean): Boolean {
        ensureFavoritesLoaded()
        val success = favoritesMutex.withLock {
            val changed = if (value) favoriteIds.add(id) else favoriteIds.remove(id)
            if (changed) {
                try {
                    val json = Json.encodeToString(favoriteIds)
                    store.write(FAVORITES_KEY, json)
                    true
                } catch (e: Exception) {
                    Logx.e(msg = "Failed to save favorites", t = e)
                    if (value) favoriteIds.remove(id) else favoriteIds.add(id)
                    false
                }
            } else {
                true
            }
        }

        if (success) {
            feedMutex.withLock {
                val itemIndex = cachedPage.indexOfFirst { it.id == id }
                if (itemIndex != -1) {
                    cachedPage[itemIndex] = cachedPage[itemIndex].copy(isFavorite = value)
                    feedStore.write(FeedSnapshotV1(
                        savedAtMillis = lastLoadMillis,
                        ttlMillis = feedTtlMillis,
                        nextCursor = cachedNextCursor,
                        items = cachedPage
                    ))
                }
            }
        }

        return success
    }

    override suspend fun fetchDashboardItemById(id: String): DashboardItem? {
        ensureFavoritesLoaded()
        ensureFeedLoaded()
        delay(300)
        return cachedPage.find { it.id == id } ?: allDashboardItems.find { it.id == id }?.copy(isFavorite = favoriteIds.contains(id))
    }

    private suspend fun loadSettingsFromStore(): MutableMap<String, Boolean> {
        val json = store.read("settings")
        return if (json != null) {
            try {
                Json.decodeFromString<MutableMap<String, Boolean>>(json)
            } catch (e: Exception) {
                Logx.e(msg = "Failed to parse settings JSON, using defaults", t = e)
                defaultSettings.toMutableMap()
            }
        } else {
            defaultSettings.toMutableMap()
        }
    }

    private suspend fun loadFavoritesFromStore(): MutableSet<String> {
        val json = store.read(FAVORITES_KEY)
        return if (json != null) {
            try {
                Json.decodeFromString<MutableSet<String>>(json)
            } catch (e: Exception) {
                Logx.w(msg = "Failed to parse favorites JSON, starting with empty set", t = e)
                mutableSetOf()
            }
        } else {
            mutableSetOf()
        }
    }
    suspend fun toggle(key: String, value: Boolean): Map<String, Boolean> = settingsMutex.withLock{
        val currentSettings = cachedSettings ?: loadSettingsFromStore()
        currentSettings[key] = value
        cachedSettings = currentSettings
        store.write("settings", Json.encodeToString(currentSettings))
        return currentSettings
    }
}

