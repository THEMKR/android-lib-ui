package com.mkr.lib.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author THEMKR
 * Base for Sqlite-Helper.
 */
open abstract class BaseDataBaseHelper : SQLiteOpenHelper {

    protected val tableMap: LinkedHashMap<String, BaseTable<*>>
    protected val context: Context

    /**
     * Enum to hold the multiple where condition joiner
     */
    enum class CONDITION_MERGER {
        AND("AND"),
        OR("OR");

        val value: String

        constructor(value: String) {
            this.value = value
        }
    }


    /**
     * Enum to hold the sort order type
     */
    enum class ORDER_BY {
        ASC("ASC"),
        DESC("DESC");

        val value: String

        constructor(value: String) {
            this.value = value
        }
    }

    /**
     * Enum to hold the value check condition
     */
    enum class WHERE {
        EQUAL("="),
        GREATER_THEN(">"),
        GREATER_THEN_EQUAL(">="),
        LESS_THEN("<"),
        LESS_THEN_EQUAL("<="),
        LIKE("LIKE"),
        LIKE_IGNORE_CASE("LIKE");

        val value: String

        constructor(value: String) {
            this.value = value
        }
    }

    /**
     * Constructor
     * @param context
     * @param databaseName
     * @param version
     */
    protected constructor(context: Context, databaseName: String, version: Int) : super(context, databaseName, null, version) {
        this.context = context
        tableMap = createTables()
    }

    abstract fun createTables(): LinkedHashMap<String, BaseTable<*>>

    override fun onCreate(db: SQLiteDatabase) {
        val tableList = tableMap.values
        for (table in tableList) {
            table.create(db)
        }
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("PRAGMA foreign_keys=ON;")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val tableList = tableMap.values
        for (table in tableList) {
            table.upgrade(db, oldVersion, newVersion)
        }
    }

    /**
     * Method to get the data in content value formate
     * @param query
     */
    open fun <MKR> getContentValue(baseTable: BaseTable<*>, query: String): ContentValues? {
        try {
            val data = getData<MKR>(baseTable, query)
            if (data != null) {
                return (baseTable as BaseTable<MKR>).parseDataToContentValues(data)
            }
        } catch (e: Exception) {
            Log.e("MKR", "getContentValue() : ${e.message}")
        }
        return null
    }


    /**
     * Method to get the data
     * @param query
     */
    open fun <MKR> getData(baseTable: BaseTable<*>, query: String): MKR? {
        try {
            val cursor = (baseTable as BaseTable<MKR>).getData(writableDatabase, query)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return baseTable.getData(cursor, true)
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.e("MKR", "getData() : ${e.message}")
        }
        return null
    }

    /**
     * Method to get the data
     * @param query
     */
    open fun <MKR> getDataList(baseTable: BaseTable<*>, query: String): ArrayList<MKR> {
        val list = ArrayList<MKR>()
        try {
            val cursor = (baseTable as BaseTable<MKR>).getData(writableDatabase, query)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val data = baseTable.getData(cursor, false)
                    if (data != null) {
                        list.add(data)
                    }
                }
                cursor.close()
            }
        } catch (e: Exception) {
            Log.e("MKR", "getData() : ${e.message}")
        }
        return list
    }

    /**
     * Method to get the data count
     * @param query
     */
    open fun <MKR> getDataCount(baseTable: BaseTable<*>, query: String): Int {
        try {
            val cursor = (baseTable as BaseTable<MKR>).getData(writableDatabase, query)
            if (cursor != null) {
                val count = cursor.count
                cursor.close()
                return count
            }
        } catch (e: Exception) {
            Log.e("MKR", "getDataCount() : ${e.message}")
        }
        return 0
    }

    /**
     * Method to save data in table
     * @param baseTable
     * @param contentValues
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    open fun saveData(baseTable: BaseTable<*>, contentValues: ContentValues): Long {
        return baseTable.saveData(writableDatabase, contentValues)
    }

    /**
     * Method to save data in table
     * @param baseTable
     * @param mkr
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    open fun <MKR> saveData(baseTable: BaseTable<*>, mkr: MKR): Long {
        return saveData(baseTable as BaseTable<MKR>, baseTable.parseDataToContentValues(mkr))
    }

    /**
     * Method to save data list in table
     * @param baseTable
     * @param mkrList
     * @return LIST OF [the row ID of the newly inserted row, or -1 if an error occurred]
     */
    open fun <MKR> saveDataList(baseTable: BaseTable<*>, mkrList: ArrayList<MKR>): ArrayList<Long> {
        val list = ArrayList<Long>()
        for (mkr in mkrList) {
            list.add(saveData(baseTable, mkr))
        }
        return list
    }

    /**
     * Method to clear the complete table
     */
    open fun clear(baseTable: BaseTable<*>): Boolean {
        return baseTable.clear(writableDatabase)
    }

    /**
     * Method to clear the data from this table
     * @param whereClause column = ?
     * @param whereArgs
     * @return
     */
    open fun clear(baseTable: BaseTable<*>, whereClause: String, whereArgs: Array<String>): Boolean {
        return baseTable.clear(writableDatabase, whereClause, whereArgs)
    }

    /**
     * Method to build the where Query Literal
     * @param column
     * @param value Int, Float, Long, String
     */
    protected open fun where(where: WHERE, column: Column, value: Any): String {
        when (where) {
            WHERE.LIKE -> {
                return if (value is Int || value is Float || value is Long) {
                    "(${column.name} ${where.value} %$value%)"
                } else {
                    "(${column.name} ${where.value} \"%$value%\")"
                }
            }
            WHERE.LIKE_IGNORE_CASE -> {
                return if (value is Int || value is Float || value is Long) {
                    "(upper(${column.name}) ${where.value} %$value%)"
                } else {
                    "(upper(${column.name}) ${where.value} \"%${value.toString().toUpperCase(Locale.getDefault())}%\")"
                }
            }
            else -> {
                return if (value is Int || value is Float || value is Long) {
                    "(${column.name} ${where.value} $value)"
                } else {
                    "(${column.name} ${where.value} \"$value\")"
                }
            }
        }
    }

    /**
     * Method to build the order Query Literal
     * @param orderBy
     * @param column
     */
    protected open fun orderBy(orderBy: ORDER_BY, column: Column): String {
        return "${column.name} ${orderBy.value}"
    }
}