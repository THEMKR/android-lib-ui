package com.lory.library.network

/**
 * @author THEMKR
 */
class NetworkResponse {
    var data: String? = null
        get() {
            return field ?: ""
        }

    var httpStatus: Int? = null
        get() {
            return field ?: -1
        }

    var respHeaderMap: HashMap<String, String>? = null
        get() {
            return field ?: HashMap<String, String>()
        }

    /**
     * Constructor
     *
     * @param httpStatus
     * @param data
     * @param respHeaderMap
     */
    constructor(httpStatus: Int, data: String, respHeaderMap: HashMap<String, String>) {
        this.httpStatus = httpStatus
        this.data = data
        this.respHeaderMap = respHeaderMap
    }
}