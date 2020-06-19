package com.lory.library.storage.application

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.lory.library.sqlite.BaseTable
import com.lory.library.sqlite.Column
import com.lory.library.sqlite.ColumnProperty
import com.lory.library.sqlite.ColumnType
import java.io.ByteArrayOutputStream

/**
 * @author THEMKR
 */
internal class TableBitmap : BaseTable<BitmapInfo> {

    companion object {
        private const val TAG: String = "TableBitmap"
        internal val COLUMN_KEY: Column = Column.Builder("key").addProperty(ColumnProperty.PRIMARY).addProperty(ColumnProperty.NOT_NULL).setType(ColumnType.STRING).build()
        internal val COLUMN_BITMAP: Column = Column.Builder("bitmap").setType(ColumnType.BYTE).build()

        private var instance: TableBitmap? = null

        /**
         * Method to get the Instance of this Table
         * @param context
         */
        fun getInstance(context: Context): TableBitmap {
            if (instance == null) {
                instance = TableBitmap(context)
            }
            return instance!!
        }
    }

    private constructor(context: Context) : super(context) {

    }

    override fun getTableName(): String {
        return DataBaseHelper.TABLE_BITMAP_INFO
    }

    override fun getColumnList(): ArrayList<Column> {
        return arrayListOf(
            COLUMN_KEY,
            COLUMN_BITMAP
        )
    }

    override fun parseCursorToData(cursor: Cursor): BitmapInfo? {
        val key = cursor.getString(cursor.getColumnIndex(COLUMN_KEY.name))
        val bitmap = byteToBitmap(cursor.getBlob(cursor.getColumnIndex(COLUMN_BITMAP.name)))
        return BitmapInfo(key, bitmap)
    }

    override fun parseDataToContentValues(mkr: BitmapInfo): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_KEY.name, mkr.key)
        contentValues.put(COLUMN_BITMAP.name, bitmapToByte(mkr.bitmap))
        return contentValues
    }

    /**
     * Method to convert bitmap into byte
     *
     * @param drawable
     * @return
     */
    private fun bitmapToByte(bitmap: Bitmap?): ByteArray? {
        try {
            val blob = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.PNG, 0, blob)
            return blob.toByteArray()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "bitmapToByte : ${e.message} ")
        }
        return null
    }

    /**
     * Method to convert byte array to bitmap
     * @param byteArray
     */
    private fun byteToBitmap(byteArray: ByteArray?): Bitmap? {
        try {
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray?.size ?: 0)
            if (bitmap != null && !bitmap.isRecycled) {
                return bitmap
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "byteToBitmap : ${e.message} ")
        }
        return null
    }
}