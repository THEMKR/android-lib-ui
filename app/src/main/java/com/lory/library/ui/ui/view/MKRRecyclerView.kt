package com.lory.library.ui.ui.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

open class MKRRecyclerView : RecyclerView {
    private var mOldOnScrollListener: RecyclerView.OnScrollListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun addOnScrollListener(listener: OnScrollListener) {
        if (mOldOnScrollListener != null) {
            removeOnScrollListener(mOldOnScrollListener!!)
        }
        mOldOnScrollListener = listener
        if (mOldOnScrollListener != null) {
            super.addOnScrollListener(mOldOnScrollListener!!)
        }
    }
}
