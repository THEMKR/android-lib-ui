package com.lory.library.sqlite

/**
 * @author THEMKR
 * Class to hold the Forigen Key Reference for Sqlite
 */
class ForeignReference {
    val table: String
    val column: Column

    constructor(table: String, column: Column) {
        this.table = table
        this.column = column
    }
}