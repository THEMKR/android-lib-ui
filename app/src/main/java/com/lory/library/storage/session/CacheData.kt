package com.lory.library.storage.session

/**
 * @author THEMKR
 */
internal class CacheData<MKR> {

    val onSessionStorageListener: OnSessionStorageListener<MKR>

    /**
     * CacheData to hold the actual key used by the user
     */
    val userData: MKR

    /**
     * Constructor called at the time of putting/updating data
     * @param userData
     * @param onSessionStorageListener
     */
    internal constructor(userData: MKR, onSessionStorageListener: OnSessionStorageListener<MKR>) {
        this.userData = userData
        this.onSessionStorageListener = onSessionStorageListener
    }

    override fun hashCode(): Int {
        return userData!!.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return userData!!.equals(other)
    }
}