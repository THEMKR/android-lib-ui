package com.lory.library.storage.application

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
 * @author THEMKR
 */
internal class PreferenceStorage {

    companion object {
        private val STORE = "LORY_STORAGE"
        private const val TAG: String = "PreferenceStorage"

        private var instance: PreferenceStorage? = null

        /**
         * Method to get the current instance of this class
         * @param context
         */
        fun getInstance(context: Context): PreferenceStorage {
            if (instance == null) {
                instance = PreferenceStorage(context)
            }
            return instance!!
        }
    }

    private val context: Context

    /**
     * Constructor
     * @param context
     */
    private constructor(context: Context) {
        this.context = context.applicationContext
    }

    /**
     * Method to check weather the value exist for this key or not
     * @param key
     */
    fun isContain(key: String): Boolean {
        return getShearedPreference().contains(key)
    }

    /**
     * Method to remove the value correspond to the key base on datatype
     * @param key
     */
    fun removeValue(key: String) {
        getShearedPreferenceEditor().remove(key)
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to get Boolean Value
     *
     * @param key Pref CacheData
     * @param defaultValue
     * @return
     */
    fun getBoolean(key: String, defaultValue: Boolean?): Boolean {
        try {
            return getShearedPreference().getBoolean(key, defaultValue ?: false)
        } catch (e: Exception) {
            return defaultValue ?: false
        }
    }

    /**
     * Method to get Boolean Value
     *
     * @param key Pref CacheData
     * @return default value FALSE
     */
    fun getBoolean(key: String): Boolean {
        return getBoolean(key, false)
    }

    /**
     * Method to set Boolean Value
     *
     * @param key Pref CacheData
     * @param value Boolean value set for key
     */
    fun setBoolean(key: String, value: Boolean) {
        getShearedPreferenceEditor().putBoolean(key, value).commit()
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to get String Value
     *
     * @param key Pref CacheData
     * @param defaultValue
     * @return default defaultValue
     */
    fun getString(key: String, defaultValue: String?): String {
        try {
            return getShearedPreference().getString(key, defaultValue ?: "") ?: ""
        } catch (e: Exception) {
            return defaultValue ?: ""
        }
    }

    /**
     * Method to get String Value
     *
     * @param key Pref CacheData
     * @return default value ""
     */
    fun getString(key: String): String {
        return getString(key, "")
    }

    /**
     * Method to set String Value
     *
     * @param key Pref CacheData
     * @param value String value set for key
     */
    fun setString(key: String, value: String) {
        getShearedPreferenceEditor().putString(key, value).commit()
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to get Float Value
     *
     * @param key Pref CacheData
     * @param defaultValue
     * @return default defaultValue
     */
    fun getFloat(key: String, defaultValue: Float?): Float {
        try {
            return getShearedPreference().getFloat(key, defaultValue ?: 0F)
        } catch (e: Exception) {
            return defaultValue ?: 0F
        }
    }

    /**
     * Method to get Float Value
     *
     * @param key Pref CacheData
     * @return default value ""
     */
    fun getFloat(key: String): Float {
        return getFloat(key, 0F)
    }

    /**
     * Method to set Float Value
     *
     * @param key Pref CacheData
     * @param value Float value set for key
     */
    fun setFloat(key: String, value: Float) {
        getShearedPreferenceEditor().putFloat(key, value).commit()
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to get Int Value
     * @param key Pref CacheData
     * @param defaultValue
     * @return default defaultValue
     */
    fun getInt(key: String, defaultValue: Int?): Int {
        try {
            return getShearedPreference().getInt(key, defaultValue ?: 0)
        } catch (e: Exception) {
            return defaultValue ?: 0
        }
    }

    /**
     * Method to get Int Value
     *
     * @param key Pref CacheData
     * @return default value ""
     */
    fun getInt(key: String): Int {
        return getInt(key, 0)
    }

    /**
     * Method to set Int Value
     *
     * @param key Pref CacheData
     * @param value Int value set for key
     */
    fun setInt(key: String, value: Int) {
        getShearedPreferenceEditor().putInt(key, value).commit()
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to get Long Value
     *
     * @param key Pref CacheData
     * @param defaultValue
     * @return default defaultValue
     */
    fun getLong(key: String, defaultValue: Long?): Long {
        try {
            return getShearedPreference().getLong(key, defaultValue ?: 0L)
        } catch (e: Exception) {
            return defaultValue ?: 0L
        }
    }

    /**
     * Method to get Long Value
     *
     * @param key Pref CacheData
     * @return default value ""
     */
    fun getLong(key: String): Long {
        return getLong(key, 0L)
    }

    /**
     * Method to set Long Value
     *
     * @param key Pref CacheData
     * @param value Long value set for key
     */
    fun setLong(key: String, value: Long) {
        getShearedPreferenceEditor().putLong(key, value).commit()
    }

    //==================================================================================================================
    //==================================================================================================================
    //==================================================================================================================

    /**
     * Method to clear the Data Store
     *
     * @param context
     */
    fun clearStore() {
        getShearedPreferenceEditor().clear().commit()
    }

    /**
     * Method to return the Data Store Prefference
     *
     * @param context
     * @return
     */
    private fun getShearedPreference(): SharedPreferences {
        return context.getSharedPreferences(STORE, Context.MODE_PRIVATE)
    }

    /**
     * caller to commit this editor
     *
     * @param context
     * @return Editor
     */
    private fun getShearedPreferenceEditor(): Editor {
        return getShearedPreference().edit()
    }
}
