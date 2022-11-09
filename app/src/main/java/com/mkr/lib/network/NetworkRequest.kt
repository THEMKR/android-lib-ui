package com.mkr.lib.network

import android.os.AsyncTask
import com.mkr.lib.asynctask.CoroutinesAsyncTask
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

/**
 * @author THEMKR
 */
class NetworkRequest {
    companion object {
        private val MAX_THREAD_COUNT: Int = 4
        private var mNetworkRequestList: Vector<NetworkRequest> = Vector<NetworkRequest>()
        private var mThreadCount: Int = 0;
        private var mWatcher: Watcher? = null;

        /**
         * Method to add the Network request in the QUEUE
         *
         * @param requestType
         * @param URL
         * @param data
         * @param headers
         * @param onNetworkRequestListener
         * @param timeOut
         * @param retryCount
         * @param mediaType
         * @param isAutoRedirect
         */
        fun addToRequestQueue(requestType: NetworkConstants.RequestType, URL: String, data: String, headers: Map<String, String>, onNetworkRequestListener: OnNetworkRequestListener, timeOut: Long, retryCount: Int, mediaType: MediaType?, isAutoRedirect: Boolean) {
            val networkRequest = NetworkRequest(requestType, URL, data, headers, onNetworkRequestListener, timeOut, retryCount, mediaType, isAutoRedirect)
            mNetworkRequestList.add(networkRequest)
            initiateWatcher()
        }

        /**
         * Method to initiate the Request Queue Watcher
         */
        private fun initiateWatcher() {
            if (mNetworkRequestList.size > 0 && (mWatcher == null || !mWatcher!!.isWatching())) {
                mWatcher = Watcher()
                mWatcher!!.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
            }
        }
    }

    private var mediaType = MediaType.parse("application/json; charset=utf-8")
    private var requestType: NetworkConstants.RequestType
    private var url: String
    private var data: String
    private var headers: HashMap<String, String>
    private var onNetworkRequestListener: OnNetworkRequestListener? = null
    private var timeOut: Long = 60000
    private var retryCount: Int = 3
    private var isAutoRedirect: Boolean = true


    /**
     * Constructor
     *
     * @param requestType
     * @param url
     * @param data
     * @param headers
     * @param onNetworkRequestListener
     * @param timeOut
     * @param retryCount
     * @param mediaType
     * @param isAutoRedirect
     */
    private constructor(requestType: NetworkConstants.RequestType, url: String, data: String, headers: Map<String, String>?, onNetworkRequestListener: OnNetworkRequestListener, timeOut: Long, retryCount: Int, mediaType: MediaType?, isAutoRedirect: Boolean) {
        this.requestType = requestType
        this.url = url
        this.data = data
        this.headers = HashMap<String, String>()
        if (headers != null) {
            this.headers.putAll(headers)
        }
        this.onNetworkRequestListener = onNetworkRequestListener
        this.timeOut = timeOut
        this.requestType = requestType
        this.retryCount = retryCount
        this.mediaType = mediaType ?: this.mediaType
        this.isAutoRedirect = isAutoRedirect
    }


    /**
     * Method to send Post Request
     *
     * @param requestType
     * @param strURL
     * @param data
     * @param headers
     * @param timeOut
     * @return
     */
    private fun executeRequest(requestType: NetworkConstants.RequestType, strURL: String, data: String, headers: Map<String, String>?, timeOut: Long): Any {
        try {
            val okHttpClientBuilder = OkHttpClient.Builder()
            okHttpClientBuilder.connectTimeout(timeOut, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.readTimeout(timeOut, TimeUnit.MILLISECONDS)
            okHttpClientBuilder.followRedirects(isAutoRedirect)
            okHttpClientBuilder.followSslRedirects(isAutoRedirect)
            val okHttpClient = okHttpClientBuilder.build()

            val requestBuilder = Request.Builder()
            var url: URL = URL(strURL)
            requestBuilder.url(url)
            if (headers != null) {
                val keys = headers.keys
                for (key in keys) {
                    requestBuilder.addHeader(key, headers[key])
                }
            }
            val requestBody = RequestBody.create(mediaType, data.toString())
            when (requestType) {
                NetworkConstants.RequestType.GET -> {
                    requestBuilder.get()
                }
                NetworkConstants.RequestType.DELETE -> {
                    requestBuilder.delete(requestBody)
                }
                NetworkConstants.RequestType.POST -> {
                    requestBuilder.post(requestBody)
                }
                NetworkConstants.RequestType.PUT -> {
                    requestBuilder.put(requestBody)
                }
                NetworkConstants.RequestType.PATCH -> {
                    requestBuilder.patch(requestBody)
                }
                else -> {
                    requestBuilder.get()
                }
            }
            val request: Request = requestBuilder.build()
            val call = okHttpClient.newCall(request)
            try {
                val response: Response = call.execute()
                val responseBodey = response.body()?.string() ?: "No Response"
                val respHeaderMap: HashMap<String, String> = HashMap<String, String>()

                val responseHeaders: Headers? = response.headers()
                if (responseHeaders != null) {
                    val keys = responseHeaders.names()
                    for (key in keys) {
                        respHeaderMap.put(key, responseHeaders.get(key) ?: "")
                    }
                }
                val code = response.code()
                call.cancel()
                return NetworkResponse(code, responseBodey, respHeaderMap)
            } catch (e: IOException) {
                e.printStackTrace()
                return NetworkError(-1, "${e.message}", -1)
            }
        } catch (e: Exception) {
            return NetworkError(-1, "${e.message}", -1)
        }
    }

    /**
     * Watcher is used to check weather the request Queue is empty or not
     */
    private class Watcher() : CoroutinesAsyncTask<Void, NetworkRequest, Void>() {
        private var mIsWatching: Boolean? = null

        init {
            mIsWatching = true
        }

        override fun doInBackground(vararg voids: Void?): Void? {
            setWatching(true)
            while (mNetworkRequestList.size > 0 || mThreadCount > 0) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                if (mThreadCount < MAX_THREAD_COUNT && mNetworkRequestList.size > 0) {
                    val networkRequest = mNetworkRequestList.get(0)
                    mNetworkRequestList.removeAt(0)
                    publishProgress(networkRequest)
                    mThreadCount++
                }
            }
            setWatching(false)
            return null
        }

        override fun onProgressUpdate(vararg values: NetworkRequest) {
            super.onProgressUpdate(*values)
            Worker(values[0]).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            setWatching(false)
            initiateWatcher()
        }

        /**
         * Method to check weather the watcher is watching or not
         *
         * @return
         */
        @Synchronized
        fun isWatching(): Boolean {
            return if (mIsWatching != null) mIsWatching!! else false
        }

        /**
         * Method to set the Watching state
         *
         * @param isWatching
         */
        @Synchronized
        private fun setWatching(isWatching: Boolean) {
            mIsWatching = isWatching
        }
    }

    /**
     * Worker class to perform the API Calling Operation
     */
    private class Worker : CoroutinesAsyncTask<Void, Void, Any> {
        private val mNetworkRequest: NetworkRequest
        private var retryCount: Int = 1

        /**
         * Constructor
         */
        constructor(networkRequest: NetworkRequest) {
            mNetworkRequest = networkRequest
            retryCount = mNetworkRequest.retryCount
        }

        override fun doInBackground(vararg p0: Void?): Any? {
            var count: Int = 0
            var result: Any? = null
            while (count < retryCount) {
                count++
                result = mNetworkRequest.executeRequest(mNetworkRequest.requestType, mNetworkRequest.url, mNetworkRequest.data, mNetworkRequest.headers, mNetworkRequest.timeOut)
                if (result is NetworkResponse) {
                    break
                }
            }
            return result
        }

        override fun onPostExecute(result: Any?) {
            super.onPostExecute(result)
            mThreadCount--
            if (result == null) {
                mNetworkRequest.onNetworkRequestListener?.onNetworkRequestFailed(NetworkError(-1, "Network Task NULL", -1))
            } else if (result is NetworkResponse) {
                mNetworkRequest.onNetworkRequestListener?.onNetworkRequestCompleted(result)
            } else if (result is NetworkError) {
                mNetworkRequest.onNetworkRequestListener?.onNetworkRequestFailed(result)
            } else {
                mNetworkRequest.onNetworkRequestListener?.onNetworkRequestFailed(NetworkError(-1, "Miss NetworkError", -1))
            }
        }
    }

}