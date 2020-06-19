package com.lory.library.network

/**
 * @author THEMKR
 */
class NetworkError {
    var errorMessage: String? = null
        get() {
            return field ?: ""
        }

    var errorCode: Int? = null
        get() {
            return field ?: -1
        }

    var httpStatus: Int? = null
        get() {
            return field ?: -1
        }

    /**
     * Constructor
     *
     * @param errorCode
     * @param errorMessage
     * @param httpStatus
     */
    constructor(errorCode: Int, errorMessage: String, httpStatus: Int) {
        this.errorCode = errorCode
        this.errorMessage = errorMessage
        this.httpStatus = httpStatus
    }
}