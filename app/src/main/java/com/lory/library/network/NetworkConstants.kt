package com.lory.library.network

/**
 * @author THEMKR
 */
interface NetworkConstants {

    /**
     * ENUM to hold the Type of API Call
     */
    enum class RequestType {
        GET, POST, DELETE, PUT, PATCH
    }

    companion object {

        val SOCKET_TIME_OUT: Long = 60000

        val MAX_RETRY = 3
    }
}
