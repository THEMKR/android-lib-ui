package com.mkr.lib.asynctask

/**
 * @author THEMKR
 */
open class BaseAsyncTaskProvider {
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
     * @param asyncCallBack
     */
    fun notifyTaskResponse(asyncCallBack: AsyncCallBack<Any, Any>, o: Any?) {
        if (mIsAttach) {
            asyncCallBack.onSuccess(o)
        }
    }

    /**
     * Method to Notify Caller
     *
     * @param asyncCallBack
     */
    fun notifyTaskProgress(asyncCallBack: AsyncCallBack<Any, Any>, o: Any?) {
        if (mIsAttach) {
            asyncCallBack.onProgress(o)
        }
    }
}
