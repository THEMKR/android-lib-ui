package com.nazgul.library.ui.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.nazgul.library.ui.R

@SuppressLint("NewApi")
open class FontTextView : TextView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    /**
     * Method to initialize the member
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView, 0, 0)
            var typeFace: String? = a?.getString(R.styleable.FontTextView_text_view_face)
            if (typeFace != null) {
                typeface = Typeface.createFromAsset(context.assets, typeFace)
            }
        }
    }
}
