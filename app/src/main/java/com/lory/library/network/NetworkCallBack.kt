package com.lory.library.network

/**
 * @author THEMKR
 */
interface NetworkCallBack<MKR> {
    /**
     * Method to notifyTaskResponse the Successful Result
     * @param mkr
     */
    fun onSuccess(mkr: ApiSuccess<MKR>)

    /**
     * Method to notifyTaskResponse that there may be some sort of error occur at the time of calling
     * @param mkr
     */
    fun onError(mkr: ApiFailed<MKR>)
}
