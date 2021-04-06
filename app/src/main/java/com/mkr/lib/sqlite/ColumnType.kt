package com.mkr.lib.sqlite

/**
 * @author THEMKR
 * Class to handle the column attribute type
 */
enum class ColumnType {
    STRING("VARCHAR"),
    INTEGER("INTEGER"),
    LONG("INTEGER"),
    FLOAT("REAL"),
    BYTE("BLOB");

    val value: String

    private constructor(value: String) {
        this.value = value
    }
}