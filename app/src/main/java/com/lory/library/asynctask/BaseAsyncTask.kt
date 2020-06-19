package com.lory.library.asynctask

import android.content.Context
import android.os.AsyncTask

/**
 * @author THEMKR
 */
abstract class BaseAsyncTask<MKR, PROGRESS> : AsyncTask<Void, PROGRESS, MKR> {

    protected var asyncCallBack: AsyncCallBack<MKR, PROGRESS>? = null
    protected val context: Context

    /**
     * Constructor
     *
     * @param context
     * @param asyncCallBack
     */
    constructor(context: Context, asyncCallBack: AsyncCallBack<MKR, PROGRESS>?) : super() {
        this.context = context;
        this.asyncCallBack = asyncCallBack;
    }

    /**
     * Method to START the task
     */
    fun executeTask() {
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * USER SHOULD NOT OVER RIDE THIS METHOD
     */
    override fun doInBackground(vararg voids: Void): MKR? {
        return this@BaseAsyncTask.doInBackground()
    }

    /**
     * USER MUST CALL THE SUPER
     */
    override fun onProgressUpdate(vararg values: PROGRESS) {
        super.onProgressUpdate(*values)
        if (values.isNotEmpty() && values[0] != null) {
            this@BaseAsyncTask.asyncCallBack?.onProgress(values[0])
        }
    }

    /**
     * USER MUST CALL THE SUPER
     */
    override fun onPostExecute(mkr: MKR?) {
        super.onPostExecute(mkr)
        asyncCallBack?.onSuccess(mkr)
    }

    /**
     * Method to do the Background task
     *
     * @param result
     * @return
     */
    protected abstract fun doInBackground(): MKR
}