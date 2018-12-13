package com.lory.library.ui.ui.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpaceItemDecoration : RecyclerView.ItemDecoration {

    private val paint: Paint = Paint()
    private var spacing: Int

    /**
     * Constructor
     *
     * @param verticalSpacing
     */
    constructor(spacing: Int, dividerColor: Int) {
        paint!!.isAntiAlias = true
        paint!!.isFilterBitmap = true
        paint!!.color = dividerColor
        paint!!.strokeWidth = spacing.toFloat()
        this.spacing = spacing

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = spacing
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val hX1 = parent.paddingLeft
        val hX2 = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val y = child.bottom + params.bottomMargin + (spacing!! shr 1)
            c.drawLine(hX1.toFloat(), y.toFloat(), hX2.toFloat(), y.toFloat(), paint)
        }
    }
}