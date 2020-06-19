package com.lory.library.storage.application

import android.content.Context
import android.util.Log
import com.lory.library.sqlite.BaseDataBaseHelper
import com.lory.library.sqlite.BaseTable
import java.util.*

/**
 * @author THEMKR
 */
internal class DataBaseHelper : BaseDataBaseHelper {

    companion object {
        private val TAG = "DataBaseHelper"
        const val STORAGE = "STORAGE"
        const val TABLE_BITMAP_INFO = "TableBitmap"
        private var instance: DataBaseHelper? = null


        /**
         * Get the instance of DataHelper
         *
         * @param context
         * @return
         */
        fun getInstance(context: Context): DataBaseHelper {
            if (instance == null) {
                instance = DataBaseHelper(context.applicationContext)
            }
            return instance!!
        }
    }

    /**
     * Constructor
     * @param context
     */
    private constructor(context: Context) : super(context, STORAGE, 2)

    override fun createTables(): LinkedHashMap<String, BaseTable<*>> {
        val hashMap = LinkedHashMap<String, BaseTable<*>>()
        hashMap[TABLE_BITMAP_INFO] = TableBitmap.getInstance(context)
        return hashMap
    }

    /**
     * Method to save the Bitmap Info Data
     * @param values
     * @return Return the new row ID or else -1
     */
    fun saveBitmap(dto: BitmapInfo): Long {
        try {
            val baseTable = tableMap[TABLE_BITMAP_INFO] as TableBitmap
            return saveData(baseTable, dto)
        } catch (e: Exception) {
            Log.e(TAG, "saveBitmap: ${e.message}")
            return -1
        }
    }

    /**
     * Method to get the BitmapInfo
     * @param key
     * @return Return BitmapInfo
     */
    fun getBitmapInfo(key: String): BitmapInfo? {
        val baseTable = tableMap[TABLE_BITMAP_INFO] ?: return null
        val query = "Select * from ${baseTable.getTableName()} WHERE ${where(WHERE.EQUAL, TableBitmap.COLUMN_KEY, key)};"
        return getData(baseTable, query)
    }

    /**
     * Method to remove the of Bitmap Info
     * @param key
     */
    fun clearBitmapInfo(key: String) {
        try {
            clear(tableMap[TABLE_BITMAP_INFO] as TableBitmap, "${TableBitmap.COLUMN_KEY.name} = ?", arrayOf(key))
        } catch (e: Exception) {
            Log.e(TAG, "clearBitmapInfo: ${e.message}")
        }
    }

    /**
     * Method to Clear DB
     */
    fun clear() {
        clearBitmapInfo()
    }

    /**
     * Method to remove the List of Bitmap Info
     */
    private fun clearBitmapInfo() {
        try {
            clear(tableMap[TABLE_BITMAP_INFO] as TableBitmap)
        } catch (e: Exception) {
            Log.e(TAG, "clearBitmapInfo: ${e.message}")
        }
    }
}
