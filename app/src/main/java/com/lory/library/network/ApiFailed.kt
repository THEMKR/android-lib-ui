package com.lory.library.network

/**
 * @author THEMKR
 */
class ApiFailed<MKR> {

    /**
     * HTTP status code received from server
     */
    val httpStatusCode: Int

    /**
     * DTO of the Response
     */
    val responseData: MKR?

    /**
     * Error Message received from server
     */
    val errorMessage: String

    /**
     * Error code received from Server
     */
    val errorCode: Int

    /**
     * Constructor
     * @param errorMessage
     * @param errorCode
     * @param httpStatusCode
     * @param responseData
     */
    constructor(errorMessage: String, errorCode: Int, httpStatusCode: Int, responseData: MKR?) {
        this.errorMessage = errorMessage
        this.errorCode = errorCode
        this.httpStatusCode = httpStatusCode
        this.responseData = responseData
    }
}