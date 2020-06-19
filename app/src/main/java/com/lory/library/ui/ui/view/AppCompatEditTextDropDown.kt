package com.lory.library.ui.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet

/**
 * @author THEMKR
 * Class to handle the Font Edittext
 */
class AppCompatEditTextDropDown : AppCompatFontEditText {
    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var mArrowSize: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mArrowSize = (lineHeight.toFloat() * 0.4f).toInt()
        if (mArrowSize % 2 != 0) {
            mArrowSize++
        }
        val paddingRight = paddingRight
        setPadding(paddingLeft, paddingTop, paddingRight + 2 * mArrowSize, paddingBottom)
    }

    /**
     * Method to set the color of the Dropdown Icon
     */
    fun setDropdownIconColor(color: Int) {
        mPaint!!.color = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var width = 0
        val measureText = paint.measureText(text.toString()).toInt()
        val writableWidth = getWidth() - paddingRight - paddingLeft
        if (measureText > writableWidth) {
            width = paddingLeft + measureText
        } else {
            width = paddingLeft + writableWidth
        }
        mPath!!.reset()
        val x1 = width + (mArrowSize shr 1)
        val x2 = x1 + mArrowSize
        val x3 = x1 + (x2 - x1 shr 1)
        val y1 = height - mArrowSize shr 1
        val y2 = y1 + mArrowSize
        mPath!!.moveTo(x1.toFloat(), y1.toFloat())
        mPath!!.lineTo(x2.toFloat(), y1.toFloat())
        mPath!!.lineTo(x3.toFloat(), y2.toFloat())
        mPath!!.lineTo(x1.toFloat(), y1.toFloat())
        canvas.drawPath(mPath!!, mPaint!!)
    }

    /**
     * Method to init the EditText
     */
    private fun init() {
        maxLines = 1
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.isFilterBitmap = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = currentTextColor
        mPath = Path()
    }
}