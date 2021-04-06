package com.mkr.lib.network

/**
 * @author THEMKR
 */
interface OnNetworkRequestListener {

    /**
     * Callback to notifyTaskResponse that the request is successfully granted by the
     * server and response is returned from the server
     *
     * @param networkResponse response return from the server in the form of string
     */
    fun onNetworkRequestCompleted(networkResponse: NetworkResponse)

    /**
     * Callback to notifyTaskResponse that there is some sort of networkError occur at the time
     * of requesting to the server
     *
     * @param networkError NetworkError occur at the time of sending request to server
     */
    fun onNetworkRequestFailed(networkError: NetworkError)
}