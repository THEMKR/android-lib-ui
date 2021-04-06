package com.mkr.lib.storage.application

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


/**
 * @author THEMKR
 * Class to hold the session sessionCache item
 */
class ApplicationStorage {
    companion object {
        private const val TAG: String = "ApplicationStorage"

        private var instance: ApplicationStorage? = null

        /**
         * Method to get the current instance of the ApplicationStorage
         * @param context
         */
        fun getInstance(context: Context): ApplicationStorage {
            if (instance == null) {
                instance = ApplicationStorage(context)
            }
            return instance!!
        }
    }

    /**
     * Hold the type of data to be saved
     */
    enum class DataType {
        BOOLEAN,
        INT,
        FLOAT,
        LONG,
        STRING,
        BITMAP,
        SERIALIZABLE
    }

    private val context: Context
    private val preferenceStorage: PreferenceStorage

    /**
     * Constructor
     * @param context
     */
    private constructor(context: Context) {
        this.context = context.applicationContext
        this.preferenceStorage = PreferenceStorage.getInstance(context)
    }

    /**
     * Method to clear the Complete Storage
     */
    fun clear() {
        // CLEAR PREF
        preferenceStorage.clearStore()
        // CLEAR BITMAP
        DataBaseHelper.getInstance(context).clear()
        // CLEAR SERIALIZABLE
        val fileList = context.fileList()
        for (fileName in fileList) {
            try {
                context.deleteFile(fileName)
            } catch (e: Exception) {
                Log.e(TAG, "clear File : ${e.message} ")
            }
        }
    }

    /**
     * Method to check weather the value exist for this key or not
     * @param key
     * @param dataType
     */
    fun isContain(key: String, dataType: DataType): Boolean {
        when (dataType) {
            DataType.BOOLEAN, DataType.INT, DataType.FLOAT, DataType.LONG, DataType.STRING -> {
                return preferenceStorage.isContain(key)
            }
            DataType.BITMAP -> {
                return DataBaseHelper.getInstance(context).getBitmapInfo(key)?.bitmap != null
            }
            DataType.SERIALIZABLE -> {
                val fileList = context.fileList()
                for (fileName in fileList) {
                    if (fileName?.equals(key) ?: false) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * Method to put an item in Storage
     * @param key
     * @param data only support [DataType]
     */
    @Synchronized
    fun put(key: String, data: Any) {
        if (data is Boolean) {
            preferenceStorage.setBoolean(key, data)
        } else if (data is Int) {
            preferenceStorage.setInt(key, data)
        } else if (data is Float) {
            preferenceStorage.setFloat(key, data)
        } else if (data is Long) {
            preferenceStorage.setLong(key, data)
        } else if (data is String) {
            preferenceStorage.setString(key, data)
        } else if (data is Bitmap) {
            DataBaseHelper.getInstance(context).saveBitmap(BitmapInfo(key, data))
        } else if (data is Serializable) {
            try {
                val fos = context.openFileOutput(key, Context.MODE_PRIVATE)
                val out = ObjectOutputStream(fos)
                out.writeObject(data)
                out.flush()
            } catch (e: Exception) {
                Log.e(TAG, "put(serializable) : ${e.message} ")
            }
        } else {
            // NOT SUPPORTED
        }
    }

    /**
     * Method to get the value from the storage correspond with the key given
     * @param key
     * @param dataType
     */
    fun <MKR> getValue(key: String, dataType: DataType): MKR? {
        return getValue(key, dataType, null)
    }

    /**
     * Method to get the value from the storage correspond with the key given
     * @param key
     * @param dataType
     * @param default
     */
    fun <MKR> getValue(key: String, dataType: DataType, default: MKR?): MKR? {
        when (dataType) {
            DataType.BOOLEAN -> {
                return preferenceStorage.getBoolean(key, default as Boolean?) as MKR?
            }
            DataType.INT -> {
                return preferenceStorage.getInt(key, default as Int?) as MKR?
            }
            DataType.FLOAT -> {
                return preferenceStorage.getFloat(key, default as Float?) as MKR?
            }
            DataType.LONG -> {
                return preferenceStorage.getLong(key, default as Long?) as MKR?
            }
            DataType.STRING -> {
                return preferenceStorage.getString(key, default as String?) as MKR?
            }
            DataType.BITMAP -> {
                return (DataBaseHelper.getInstance(context).getBitmapInfo(key)?.bitmap as MKR?) ?: default
            }
            DataType.SERIALIZABLE -> {
                try {
                    val fin = context.openFileInput(key)
                    val input = ObjectInputStream(fin)
                    return input.readObject() as MKR?
                } catch (e: Exception) {
                    return default
                }
            }
        }
        return default
    }

    /**
     * Method to remove the value correspond to the key base on datatype
     * @param key
     * @param dataType
     */
    fun removeValue(key: String, dataType: DataType) {
        when (dataType) {
            DataType.BOOLEAN, DataType.INT, DataType.FLOAT, DataType.LONG, DataType.STRING -> {
                PreferenceStorage.getInstance(context).removeValue(key)
            }
            DataType.BITMAP -> {
                DataBaseHelper.getInstance(context).clearBitmapInfo(key)
            }
            DataType.SERIALIZABLE -> {
                context.deleteFile(key)
            }
        }
    }
}