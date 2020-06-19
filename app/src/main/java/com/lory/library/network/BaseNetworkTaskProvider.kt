package com.lory.library.network

import android.os.Handler
import android.os.Looper

/**
 * @author THEMKR
 */
open class BaseNetworkTaskProvider {
    private var mIsAttach: Boolean = false

    /**
     * Method to attach network provider
     */
    fun attachProvider() {
        mIsAttach = true
    }

    /**
     * Method to detach network provider
     */
    fun detachProvider() {
        mIsAttach = false
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     * @param apiSuccess
     */
    fun notifyTaskResponse(networkCallBack: NetworkCallBack<Any>, apiSuccess: ApiSuccess<Any>) {
        if (mIsAttach) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                networkCallBack.onSuccess(apiSuccess)
            } else {
                Handler(Looper.getMainLooper()).post(object : Runnable {
                    override fun run() {
                        networkCallBack.onSuccess(apiSuccess)
                    }
                })
            }
        }
    }

    /**
     * Method to Notify Caller
     *
     * @param networkCallBack
     * @param apiFailed
     */
    fun notifyTaskResponse(networkCallBack: NetworkCallBack<Any>, apiFailed: ApiFailed<Any>) {
        if (mIsAttach) {
            if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                networkCallBack.onError(apiFailed)
            } else {
                Handler(Looper.getMainLooper()).post(object : Runnable {
                    override fun run() {
                        networkCallBack.onError(apiFailed)
                    }
                })
            }
        }
    }
}
