package com.nazgul.library.androidlibui.ui.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet

/**
 * Created by A1ZFKXA3 on 8/21/2017.
 */

class MKRViewPager : ViewPager {
    private var mOnPageChangeListener: OnPageChangeListener? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun addOnPageChangeListener(listener: OnPageChangeListener) {
        if (mOnPageChangeListener != null) {
            removeOnPageChangeListener(mOnPageChangeListener!!)
        }
        mOnPageChangeListener = listener
        if (mOnPageChangeListener != null) {
            super.addOnPageChangeListener(mOnPageChangeListener!!)
        }
    }
}
