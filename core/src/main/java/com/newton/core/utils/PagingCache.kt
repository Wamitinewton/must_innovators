package com.newton.core.utils

class PagingCache<K, V>(private val maxSize: Int) {
    private val cache = LinkedHashMap<K, V>(maxSize, 0.75f, true)

    @Synchronized
    fun get(key: K): V? = cache[key]

    @Synchronized
    fun put(key: K, value: V) {
        if (cache.size >= maxSize) {
            cache.remove(cache.keys.first())
        }
        cache[key] = value
    }

    @Synchronized
    fun clear() = cache.clear()
}