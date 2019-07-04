package com.lory.library.ui.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.lory.library.ui.R

class AppCompatFontAutoCompleteTextView : AppCompatAutoCompleteTextView {

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    /**
     * Method to initialize the member
     */
    private fun init(attrs: AttributeSet?) {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AppCompatFontAutoCompleteTextView, 0, 0)
            var typeFace: String? = a?.getString(R.styleable.AppCompatFontAutoCompleteTextView_app_compat_auto_complete_text_view_face)
            if (typeFace != null) {
                typeface = Typeface.createFromAsset(context.assets, typeFace)
            }
        }
    }
}