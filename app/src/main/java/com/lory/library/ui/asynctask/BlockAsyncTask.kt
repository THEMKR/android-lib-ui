package com.lory.library.ui.asynctask

import android.content.Context

/**
 * Created by mkr on 3/4/18.
 */
abstract class BaseBlockAsyncTask<MKR, PROGRESS> : BaseAsyncTask<MKR, PROGRESS> {

    private var isExecutionLock: Boolean = false

    /**
     * Constructor
     * @param context
     * @param asyncCallBack
     */
    constructor(context: Context, asyncCallBack: AsyncCallBack<MKR, PROGRESS>?) : super(context, asyncCallBack) {
    }


    /**
     * Method to set the value of lock
     */
    @Synchronized
    protected fun setLock(isExecutionLock: Boolean) {
        this.isExecutionLock = isExecutionLock
    }

    /**
     * Method to check weather the isExecutionLock is enable of disable.
     */
    @Synchronized
    protected fun isTaskLock(): Boolean {
        return isExecutionLock
    }

    /**
     * Function used to block the call. User must call [setLock] TRUE before calling this method.
     * It return only when task is [setLock] FALSE
     */
    @Synchronized
    protected fun loopLock() {
        while (isTaskLock()) {
            try {
                Thread.sleep(50)
            } catch (e: Exception) {

            }
        }
    }
}