package com.mkr.lib.ui.callback

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * @author THEMKR
 */
interface OnBaseActivityListener {

    /**
     * Method to replace fragment
     *
     * @param fragment Fragment to be loaded
     * @param bundle   Bundle sat as argument
     * @param tag      Fragment Tag
     */
    fun onBaseActivityReplaceFragment(fragment: Fragment, bundle: Bundle?, tag: String)

    /**
     * Method to replace fragment
     *
     * @param containerId fragment container Id
     * @param fragment    Fragment to be loaded
     * @param bundle      Bundle sat as argument
     * @param tag         Fragment Tag
     */
    fun onBaseActivityReplaceFragment(containerId: Int, fragment: Fragment, bundle: Bundle?, tag: String)

    /**
     * Method to add fragment
     *
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    fun onBaseActivityAddFragment(fragment: Fragment, bundle: Bundle?, isAddToBackStack: Boolean, tag: String)

    /**
     * Method to add fragment
     *
     * @param containerId      Fragment container Id
     * @param fragment         Fragment to be loaded
     * @param bundle           Bundle sat as argument
     * @param isAddToBackStack TRUE if need to save fragment in back stack
     * @param tag              Fragment Tag
     */
    fun onBaseActivityAddFragment(containerId: Int, fragment: Fragment, bundle: Bundle?, isAddToBackStack: Boolean, tag: String)

    /**
     * Method to set the Title of the Screen
     */
    fun onBaseActivitySetScreenTitle(title: String)

    /**
     * Method to set the Toolbar of the Screen
     */
    fun onBaseActivitySetToolbar(toolbarLayout: View)

}
