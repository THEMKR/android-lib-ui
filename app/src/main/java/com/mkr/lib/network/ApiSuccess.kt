package com.mkr.lib.network

/**
 * @author THEMKR
 */
class ApiSuccess<MKR> {

    /**
     * HTTP status code received from server
     */
    val httpStatusCode: Int

    /**
     * DTO of the Response
     */
    val responseData: MKR?

    /**
     * Constructor
     * @param httpStatusCode
     * @param responseData
     */
    constructor(httpStatusCode: Int, responseData: MKR?) {
        this.httpStatusCode = httpStatusCode
        this.responseData = responseData
    }
}