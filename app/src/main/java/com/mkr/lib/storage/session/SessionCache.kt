package com.mkr.lib.storage.session

import android.util.LruCache

/**
 * @author THEMKR
 */
internal class SessionCache : LruCache<String, CacheData<*>> {

    companion object {
        private const val TAG: String = "SessionCache"
        private var instance: SessionCache? = null

        /**
         * Method to get the current instance of the SessionCache
         */
        fun getInstance(): SessionCache {
            if (instance == null) {
                instance = SessionCache()
            }
            return instance!!
        }
    }

    private constructor() : super((Runtime.getRuntime().maxMemory() / 4L).toInt()) {

    }

    override fun entryRemoved(evicted: Boolean, key: String?, oldValue: CacheData<*>?, newValue: CacheData<*>?) {
        super.entryRemoved(evicted, key, oldValue, newValue)
        (oldValue?.onSessionStorageListener as OnSessionStorageListener<Any?>).onItemRecycled(oldValue.userData)
    }

    override fun sizeOf(key: String?, value: CacheData<*>?): Int {
        val size = (value?.onSessionStorageListener as OnSessionStorageListener<Any?>).onItemSizeInByte(value.userData)
        return size
    }
}