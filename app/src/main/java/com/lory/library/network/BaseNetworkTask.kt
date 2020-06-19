package com.lory.library.network

import android.content.Context
import android.os.AsyncTask
import okhttp3.MediaType
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*

/**
 * @author THEMKR
 */
abstract class BaseNetworkTask<MKR> {
    protected var networkCallBack: NetworkCallBack<MKR>? = null
    protected var request: String
    protected var context: Context
    protected var header: HashMap<String, String>

    /**
     * Constructor
     *
     * @param context
     * @param request It may be JSON or Plane Text. Data will manage on runTime
     * @param networkCallBack
     */
    constructor(context: Context, request: String, networkCallBack: NetworkCallBack<MKR>?) : this(context, request, networkCallBack, null) {

    }

    /**
     * Constructor
     *
     * @param context
     * @param request It may be JSON or Plane Text. Data will manage on runTime
     * @param networkCallBack
     * @param header
     */
    constructor(context: Context, request: String, networkCallBack: NetworkCallBack<MKR>?, header: HashMap<String, String>?) {
        this.context = context
        this.networkCallBack = networkCallBack
        this.request = request
        this.header = HashMap<String, String>()
        if (header != null) {
            this.header.putAll(header)
        }
    }

    /**
     * Method to Execute the network call.<br></br>This Method is run on back thread
     */
    fun executeTask() {
        object : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                preExecute()
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                this@BaseNetworkTask.execute()
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to Execute the network call.<br></br>This Method is run on back thread
     */
    private fun execute() {
        if (isUsedLocalResponse()) {
            networkCallBack?.onSuccess(ApiSuccess(-1, parseNetworkResponse(getLocalResponseJson())))
            return
        }
        if (isActualNetworkConnected()) {
            ConnectivityInfoUtils.isConnected(context, object : ConnectivityInfoUtils.OnConnectivityInfoUtilsListener {
                override fun onConnectivityInfoUtilsNetworkConnected() {
                    callApi()
                }

                override fun onConnectivityInfoUtilsNetworkDisconnected() {
                    networkCallBack?.onError(ApiFailed("No Network Connection", -1, -1, null))
                }
            })
        } else if (ConnectivityInfoUtils.isConnected(context, false)) {
            callApi()
        } else {
            networkCallBack?.onError(ApiFailed("No Network Connection", -1, -1, null))
        }
    }

    /**
     * Method to actually call the Server.<br>
     * If User want to OVER-LOAD this method then, REMOVE-THE-SUPER-CALL, and call processHttpNetworkResponse(networkResponse: NetworkResponse), processHttpFailureResponse(networkError: NetworkError) from the same thread from which this method is called
     */
    protected open fun callApi() {
        var url = getUrl()
        if (getRequestType().equals(NetworkConstants.RequestType.GET)) {
            url = getGETUrlWithParam(url, request)
        }
        url = urlInterceptor(url)
        NetworkRequest.addToRequestQueue(getRequestType(), url, request, header, object : OnNetworkRequestListener {
            override fun onNetworkRequestCompleted(networkResponse: NetworkResponse) {
                processHttpNetworkResponse(networkResponse)
            }

            override fun onNetworkRequestFailed(networkError: NetworkError) {
                processHttpFailureResponse(networkError)
            }
        }, getTimeOut(), getRetryCount(), getMediaType(), isCallAutoRedirect())
    }

    /**
     * Method called when Http API calling gives you Http-Success response
     * @author If Override then should call super at the END
     */
    protected open fun processHttpNetworkResponse(networkResponse: NetworkResponse) {
        if (!isBusinessResponseSuccess(networkResponse.data!!)) {
            val businessResponseErrorMessage = getBusinessResponseErrorMessage(networkResponse.data!!)
            val businessResponseErrorCode = getBusinessResponseErrorCode(networkResponse.data!!)
            networkCallBack?.onError(ApiFailed(businessResponseErrorMessage, businessResponseErrorCode, networkResponse.httpStatus!!, parseNetworkResponse(networkResponse.data!!)))
            return
        }
        object : AsyncTask<Void, Void, MKR>() {

            override fun doInBackground(vararg voids: Void): MKR? {
                return parseNetworkResponse(networkResponse.data!!)
            }

            override fun onPostExecute(mkr: MKR?) {
                super.onPostExecute(mkr)
                networkCallBack?.onSuccess(ApiSuccess(networkResponse.httpStatus!!, mkr))
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method called when Http API calling gives you Http-Failure response
     * @author If Override then should call super at the END
     */
    protected open fun processHttpFailureResponse(networkError: NetworkError) {
        networkCallBack?.onError(ApiFailed(networkError.errorMessage!!, networkError.errorCode!!, networkError.httpStatus!!, null))
    }

    /**
     * Method to get the timeout time
     */
    protected open fun getTimeOut(): Long {
        return NetworkConstants.SOCKET_TIME_OUT
    }

    /**
     * Method to get the retry count
     */
    protected open fun getRetryCount(): Int {
        return NetworkConstants.MAX_RETRY
    }

    /**
     * Method to check weather the task fetch response from local storage or not
     *
     * @return
     */
    protected open fun isUsedLocalResponse(): Boolean {
        return false
    }

    /**
     * Method to check weather Call Auto Redirected or not
     *
     * @return TRUR if call auto redirected, else FALSE [DEFAULT TRUE]
     */
    protected open fun isCallAutoRedirect(): Boolean {
        return true
    }

    /**
     * Method to check weather Call Auto Redirected or not. If override then be sure about the return value
     *  @param finalUrl
     * @return The received URL
     */
    protected open fun urlInterceptor(finalUrl: String): String {
        return finalUrl
    }

    /**
     * Method to check the network connectivity of adapter or actual network
     *
     * @return TRUE to check actual network connectivity, FALSE check Adapter connectivity
     */
    protected open fun isActualNetworkConnected(): Boolean {
        return false
    }

    /**
     * Method to check the network connectivity of adapter or actual network
     *
     * @return TRUE to check actual network connectivity, FALSE check Adapter connectivity
     */
    protected open fun getMediaType(): MediaType? {
        return MediaType.parse("application/json; charset=utf-8")
    }

    /**
     * Method to Execute before the network call.<br></br>This Method is run on back thread
     */
    protected open fun preExecute() {
        // Do whatever you want to
    }

    /**
     * Method to parse the API Response in the required Model
     *
     * @param responseString
     * @return
     */
    protected open abstract fun parseNetworkResponse(responseString: String): MKR?


    /**
     * Method to check weather the Business Response is successful or not<br></br>
     * Ex : In response JSON code = 0 means success, AND code = 1 means failed
     *
     * @param responseString
     * @return
     */
    protected open abstract fun isBusinessResponseSuccess(responseString: String): Boolean

    /**
     * Method to get Business Response error message<br></br>
     *
     * @param responseString
     * @return
     */
    protected open abstract fun getBusinessResponseErrorMessage(responseString: String): String

    /**
     * Method to get Business Response error code<br></br>
     *
     * @param responseString
     * @return
     */
    protected open abstract fun getBusinessResponseErrorCode(responseString: String): Int

    /**
     * Method to get the API URL
     *
     * @return
     */
    abstract open fun getUrl(): String

    /**
     * Method to get the local response JSON path<br></br>Json should be save in ASSETS folder
     *
     * @return
     */
    protected open abstract fun getLocalResponseJson(): String

    /**
     * Method to get the Request Type
     *
     * @return
     */
    protected open abstract fun getRequestType(): NetworkConstants.RequestType


    /**
     * Method to get the URL based on Param
     *
     * @param url
     * @param jsonString
     * @return
     */
    private fun getGETUrlWithParam(url: String, jsonString: String): String {
        if (jsonString.trim().isEmpty()) {
            return url
        }
        try {
            val jsonObject = JSONObject(jsonString)
            if (jsonObject.length() == 0) {
                return url
            }
            var url = url
            var param = ""
            val keys = jsonObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                val s = jsonObject.optString(key).trim()
                if (!s.isEmpty()) {
                    try {
                        param += "&" + key + "=" + URLEncoder.encode(s, "UTF-8")
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }

                }
            }
            param = param.trim()
            if (param.length > 0) {
                param = param.substring(1).trim()
                url += "?$param"
            }
            return url
        } catch (e: java.lang.Exception) {
            return url
        }
    }
}