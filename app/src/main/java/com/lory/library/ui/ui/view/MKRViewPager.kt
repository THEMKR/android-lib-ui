package com.lory.library.ui.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

/**
 * @author THEMKR
 * Class to handle the ViewPager
 */
open class MKRViewPager : ViewPager {
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
