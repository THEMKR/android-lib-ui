package com.lory.library.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

/**
 * @author THEMKR
 * Base for Every table of sSQ-Lite. Every table class must exted it
 */
abstract class BaseTable<MKR> {

    val context: Context

    /**
     * Constructor
     * @param context
     */
    constructor(context: Context) {
        this.context = context
    }

    /**
     * Method to get the Name of the Table
     */
    abstract fun getTableName(): String

    /**
     * Method to get the List of All the column
     */
    abstract fun getColumnList(): ArrayList<Column>

    /**
     * Method to get parse the cursor to data
     * @param cursor
     */
    abstract fun parseCursorToData(cursor: Cursor): MKR?

    /**
     * Method to get parse the data to content value
     * @param mkr
     */
    abstract fun parseDataToContentValues(mkr: MKR): ContentValues


    /**
     * Method to get the data
     * @param cursor
     * @param isClosedTheCursor If true then close the cursor, else do nothing
     */
    open fun getData(cursor: Cursor, isClosedTheCursor: Boolean): MKR? {
        return try {
            parseCursorToData(cursor)
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : getData() : ${e.message}")
            null
        } finally {
            if (isClosedTheCursor) {
                cursor.close()
            }
        }
    }

    /**
     * Method to get the data
     * @param database
     * @param query
     */
    fun getData(database: SQLiteDatabase, query: String): Cursor? {
        return try {
            database.rawQuery(query, null)
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : getData() : ${e.message}")
            null
        }
    }

    /**
     * Method to save data in DataBase
     * @param database
     * @param contentValues
     * @return the row ID of the newly inserted row, or -1 if an error occurred
     */
    open fun saveData(database: SQLiteDatabase, contentValues: ContentValues): Long {
        try {
            return database.replace(getTableName(), null, contentValues)
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : saveData() : ${e.message}")
        }
        return -1
    }

    /**
     * Method to clear the complete table
     * @param database
     */
    open fun clear(database: SQLiteDatabase): Boolean {
        try {
            database.execSQL("delete from ${getTableName()}")
            return true
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : clear() : ${e.message}")
        }
        return false
    }

    /**
     * Method to clear the data from this table
     * @param database
     * @param whereClause column = ?
     * @param whereArgs
     * @return
     */
    open fun clear(database: SQLiteDatabase, whereClause: String, whereArgs: Array<String>): Boolean {
        try {
            database.delete(getTableName(), whereClause, whereArgs)
            return true
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : clear() ${e.message}")
        }
        return false
    }

    /**
     * Create the Table
     * @param database
     */
    open fun create(database: SQLiteDatabase) {
        val columnList = getColumnList()
        if (columnList.size == 0) {
            throw Exception("Atleast create png1 column in the table ${this.javaClass.canonicalName}")
        }
        if (!isTableHavePrimaryColumn(columnList)) {
            throw Exception("Atleast 1 column value should be treated as primary key in the table ${this.javaClass.canonicalName}")
        }
        try {
            var query: String = ""
            for (column in columnList) {
                query += "${createColumnQuery(column)}, "
            }
            query = query.substring(0, query.length - 2).trim()
            for (column in columnList) {
                if (isHaveProperty(column.propertyList, ColumnProperty.FOREIGN_KEY) && column.foreignReference != null) {
                    query += ", FOREIGN KEY(${column.name}) REFERENCES ${column.foreignReference.table} (${column.foreignReference.column.name})"
                }
            }
            query = "Create table ${getTableName()}($query)"
            Log.e("MKR", "${this.javaClass.canonicalName} : create() QUERY : ${query}")
            database.execSQL(query)
        } catch (e: Exception) {
            Log.e("MKR", "${this.javaClass.canonicalName} : create() EXCEPTION : ${e.message}")
        }
    }

    /**
     * Method should call when DB-Version is update. Override This if u want to persist your Data
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    open fun upgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        database.execSQL("DROP TABLE IF EXISTS ${getTableName()}")
        create(database)
    }

    /**
     * Method to create the Column Query
     * @param column
     */
    protected open fun createColumnQuery(column: Column): String {
        var query: String = "${column.name} ${column.type.value} "
        val propertyList = column.propertyList
        if (isHaveProperty(propertyList, ColumnProperty.NON)) {
            return query
        }
        val propertyPriorityOrder = getPropertyPriorityOrder()
        for (property in propertyPriorityOrder) {
            if (isHaveProperty(propertyList, property)) {
                query += "${property.value} "
            }
        }
        return query.trim()
    }

    /**
     * Method to get the value of the proprity of the Column Property Value
     */
    protected open fun getPropertyPriorityOrder(): ArrayList<ColumnProperty> {
        val list = ArrayList<ColumnProperty>()
        list.add(ColumnProperty.PRIMARY)
        list.add(ColumnProperty.AUTOINCREMENT)
        list.add(ColumnProperty.NOT_NULL)
        return list
    }

    /**
     * Method to check weather the Table have a primary column or not
     * @param columnList
     */
    private fun isTableHavePrimaryColumn(columnList: ArrayList<Column>): Boolean {
        for (column in columnList) {
            val propertyList = column.propertyList
            for (property in propertyList) {
                if (property.equals(ColumnProperty.PRIMARY)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Method to check weather the Property List have the Property or not
     * @param propertyList
     * @param property
     */
    private fun isHaveProperty(propertyList: Array<ColumnProperty>, property: ColumnProperty): Boolean {
        for (propertyItem in propertyList) {
            if (propertyItem == property) {
                return true
            }
        }
        return false
    }
}
