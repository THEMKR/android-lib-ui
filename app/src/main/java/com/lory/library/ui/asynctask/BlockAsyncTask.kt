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
     * Method to remove the loopingLock
     */
    @Synchronized
    protected fun stopTaskLoopingLock() {
        isExecutionLock = false
    }

    /**
     * Method to init the loopingLock but but user must call [startTaskLoopingLock] to start the lock
     */
    @Synchronized
    protected fun setTaskLoopingLock() {
        isExecutionLock = true
    }

    /**
     * Method to check weather the isExecutionLock is enable of disable.
     */
    @Synchronized
    protected fun isTaskLock(): Boolean {
        return isExecutionLock
    }

    /**
     * Function used to block the call. User must call [setTaskLoopingLock] before calling this method.
     * It return only when task is [stopTaskLoopingLock] called
     */
    @Synchronized
    protected fun startTaskLoopingLock() {
        while (isTaskLock()) {
            try {
                Thread.sleep(10)
            } catch (e: Exception) {

            }
        }
    }
}