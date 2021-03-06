package com.mkr.lib.ui.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @author THEMKR
 */
abstract class BaseAdapterItemHandler {

    /**
     * Method to get the Adapter View
     *
     * @param inflater
     * @param parent
     * @param viewType
     * @return
     */
    abstract fun createHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): BaseViewHolder<*>
}
