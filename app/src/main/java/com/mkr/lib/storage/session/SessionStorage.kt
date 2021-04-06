package com.mkr.lib.storage.session

import android.content.Context

/**
 * Class to hold the session sessionCache item
 */
class SessionStorage {
    companion object {
        private const val TAG: String = "SessionStorage"

        private var instance: SessionStorage? = null

        /**
         * Method to get the current instance of this class
         * @param context
         */
        fun getInstance(context: Context): SessionStorage {
            if (instance == null) {
                instance = SessionStorage(context)
            }
            return instance!!
        }
    }

    private val context: Context
    private val sessionCache: SessionCache

    /**
     * Constructor
     * @param context
     */
    private constructor(context: Context) {
        this.context = context.applicationContext
        this.sessionCache = SessionCache.getInstance()
    }

    /**
     * Method to clear the Complete Storage
     */
    fun clear() {
        sessionCache.evictAll()
    }

    /**
     * Method to check weather the value exist for this key or not
     * @param key
     */
    fun isContain(key: String): Boolean {
        return sessionCache.snapshot()?.containsKey(key) ?: false
    }

    /**
     * Method to put an item in Storage
     * @param key
     * @param data
     * @param onSessionStorageListener
     */
    @Synchronized
    fun <MKR> put(key: String, data: MKR, onSessionStorageListener: OnSessionStorageListener<MKR>) {
        sessionCache.put(key, CacheData(data, onSessionStorageListener))
    }

    /**
     * Method to remove the value from the storage correspond with the Key given
     * @param key
     */
    fun removeValue(key: String) {
        sessionCache.remove(key)
    }

    /**
     * Method to get the value from the storage correspond with the key given
     * @param key
     */
    fun <MKR> getValue(key: String): MKR? {
        try {
            return sessionCache.get(key).userData as MKR
        } catch (e: Exception) {

        }
        return null
    }
}