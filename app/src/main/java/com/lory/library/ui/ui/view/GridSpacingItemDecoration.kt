package com.lory.library.ui.ui.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration : RecyclerView.ItemDecoration {
    private val paint: Paint = Paint()
    private val spanCount: Int
    private val spacing: Int
    private val isIncludeEdge: Boolean

    /**
     * Constructor
     *
     * @param spanCount
     * @param spacing
     * @param dividerColor
     * @param isIncludeEdge
     */
    constructor(spanCount: Int, spacing: Int, dividerColor: Int, isIncludeEdge: Boolean) {
        paint!!.isAntiAlias = true
        paint!!.isFilterBitmap = true
        paint!!.color = dividerColor
        paint!!.strokeWidth = spacing.toFloat()
        this.spanCount = spanCount
        this.spacing = spacing
        this.isIncludeEdge = isIncludeEdge
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount!! // item column
        if (isIncludeEdge!!) {
            outRect.left = spacing!! - column * spacing!! / spanCount!! // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing!! / spanCount!! // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount!!) { // top edge
                outRect.top = spacing!!
            }
            outRect.bottom = spacing!! // item bottom
        } else {
            outRect.left = column * spacing!! / spanCount!! // column * ((1f / spanCount) * spacing)
            outRect.right = spacing!! - (column + 1) * spacing!! / spanCount!! // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount!!) {
                outRect.top = spacing!! // item top
            }
        }
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
            if ((i + 1) % spanCount!! != 0) {
                val vX = child.right - params.rightMargin + (spacing!! shr 1)
                val vY1 = child.top - params.topMargin
                val vY2 = child.bottom - params.bottomMargin
                c.drawLine(vX.toFloat(), vY1.toFloat(), vX.toFloat(), vY2.toFloat(), paint)
            }
        }
    }
}