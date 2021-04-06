package com.mkr.lib.storage.application

import android.graphics.Bitmap

/**
 * @author THEMKR
 */
internal class BitmapInfo {

    /**
     * Bitmap Key
     */
    val key: String

    /**
     * Bitmap
     */
    val bitmap: Bitmap?

    constructor(key: String, bitmap: Bitmap?) {
        this.key = key
        this.bitmap = bitmap
    }

    override fun toString(): String {
        return super.toString() + " : $key : $bitmap"
    }
}