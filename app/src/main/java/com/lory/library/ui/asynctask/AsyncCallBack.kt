package com.lory.library.ui.asynctask

/**
 * Created by A1ZFKXA3 on 1/30/2018.
 */

interface AsyncCallBack<MKR, PROGRESS> {
    /**
     * Method to notifyTaskResponse the Successful Result
     *
     * @param mkr
     */
    fun onSuccess(mkr : MKR?)

    /**
     * Method to notifyTaskResponse the progress Result
     *
     * @param mkr
     */
    fun onProgress(progress : PROGRESS?)
}
