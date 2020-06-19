package com.lory.library.storage.session

/**
 * @author THEMKR
 */
interface OnSessionStorageListener<MKR> {

    /**
     * Method to get the Item size in byte
     * @param mkr Data to be saved in storage
     */
    fun onItemSizeInByte(mkr: MKR): Int


    /**
     * Method to get the Item size in byte
     * @param mkr Data to be saved in storage
     */
    fun onItemRecycled(mkr: MKR): Int
}