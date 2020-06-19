package com.lory.library.asynctask

/**
 * @author THEMKR
 */
interface AsyncCallBack<MKR, PROGRESS> {
    /**
     * Method to notifyTaskResponse the Successful Result
     *
     * @param mkr
     */
    fun onSuccess(mkr: MKR?)

    /**
     * Method to notifyTaskResponse the progress Result
     *
     * @param mkr
     */
    fun onProgress(progress: PROGRESS?)
}
