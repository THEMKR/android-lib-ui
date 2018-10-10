package com.mkrworld.androidlibui.ui.view

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class VerticalSpaceItemDecoration : RecyclerView.ItemDecoration {

    private var verticalSpacing: Int

    /**
     * Constructor
     *
     * @param verticalSpacing
     */
    constructor(verticalSpacing: Int) {
        this.verticalSpacing = verticalSpacing
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = verticalSpacing;
    }
}