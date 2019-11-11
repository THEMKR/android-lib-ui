package com.lory.library.ui.asynctask

import android.content.Context

/**
 * Created by mkr on 3/4/18.
 */
abstract class BaseBlockAsyncTask<MKR, PROGRESS> : BaseAsyncTask<MKR, PROGRESS> {

    private var isExecutionLock: Boolean = false
    private val any = Any()

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
    protected fun setLock(isExecutionLock: Boolean) {
        synchronized(any) {
            this.isExecutionLock = isExecutionLock
        }
    }

    /**
     * Method to check weather the isExecutionLock is enable of disable.
     */
    protected fun isTaskLock(): Boolean {
        synchronized(any) {
            return isExecutionLock
        }
    }

    /**
     * Function used to block the call. User must call [setLock] TRUE before calling this method.
     * It return only when task is [setLock] FALSE
     */
    protected fun loopLock() {
        while (isTaskLock()) {
            try {
                Thread.sleep(20)
            } catch (e: Exception) {

            }
        }
    }
}