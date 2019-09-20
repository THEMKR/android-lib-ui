package com.lory.library.ui.asynctask
import android.content.Context
import android.os.AsyncTask

/**
 * Created by mkr on 3/4/18.
 */
abstract class BaseAsyncTask<MKR, PROGRESS> {
    protected var asyncCallBack: AsyncCallBack<MKR, PROGRESS>? = null
    protected val context: Context
    private var asyncTask: MKRAsynctask? = null

    /**
     * Constructor
     *
     * @param context
     * @param asyncCallBack
     */
    constructor(context: Context, asyncCallBack: AsyncCallBack<MKR, PROGRESS>?) {
        this.context = context;
        this.asyncCallBack = asyncCallBack;
    }

    /**
     * Method to Execute the network call.<br></br>This Method is run on back thread
     */
    fun executeTask() {
        asyncTask = MKRAsynctask()
        asyncTask?.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
    }

    /**
     * Method to Execute before the asyncTask start background execution
     */
    protected fun preExecute() {
        // Do whatever you want to
    }

    /**
     * Method to publish the Progress Of execution, This will called from Only doInBackground()
     */
    protected fun publishProgress(progress: PROGRESS) {
        asyncTask?.publishProgressUpdate(progress)
    }

    /**
     * Method to Execute after the asyncTask response
     */
    protected fun postExecute(mkr: MKR?): MKR? {
        return mkr
    }

    /**
     * Method to do the Background task
     *
     * @param result
     * @return
     */
    protected abstract fun doInBackground(): MKR

    private inner class MKRAsynctask : AsyncTask<Void, PROGRESS, MKR>() {
        override fun onPreExecute() {
            super.onPreExecute()
            this@BaseAsyncTask.preExecute()
        }

        override fun doInBackground(vararg voids: Void): MKR? {
            return this@BaseAsyncTask.doInBackground()
        }

        override fun onProgressUpdate(vararg values: PROGRESS) {
            super.onProgressUpdate(*values)
            if (values.isNotEmpty() && values[0] != null) {
                asyncCallBack?.onProgress(values[0])
            }
        }

        override fun onPostExecute(mkr: MKR?) {
            super.onPostExecute(mkr)
            asyncCallBack?.onSuccess(this@BaseAsyncTask.postExecute(mkr))
        }

        fun publishProgressUpdate(progress: PROGRESS) {
            publishProgress(progress)
        }
    }
}