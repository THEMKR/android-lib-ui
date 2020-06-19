package com.lory.library.sqlite

/**
 * @author THEMKR
 * Class to hold the column property
 */
enum class ColumnProperty {
    NON(""),
    PRIMARY("PRIMARY KEY"),
    AUTOINCREMENT("AUTOINCREMENT"),
    NOT_NULL("NOT NULL"),
    FOREIGN_KEY("FOREIGN KEY");

    val value: String

    /**
     * Constructor
     * @param value
     */
    private constructor(value: String) {
        this.value = value
    }
}