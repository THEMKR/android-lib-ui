package com.lory.library.ui.callback

import android.os.Bundle

/**
 * Created by a1zfkxa3 on 11/28/2017.
 */

interface OnBaseFragmentListener {

    /**
     * Method to notifyTaskResponse that user pressed the back button
     *
     * @return TRUE if fragment handel the back pressed event, else return FALSE
     */
    fun onBackPressed(): Boolean

    /**
     * Method to notifyTaskResponse that this fragment is popped from back stack and now visible to the user
     */
    fun onPopFromBackStack()

    /**
     * Method to notifyTaskResponse the there is a need to forcefully refresh the fragment view
     */
    fun onRefresh()

    /**
     * Method to called from this method of Activity
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    fun onPermissionsResult(requestCode: Int, permissions: Array<String>?, grantResults: IntArray)

    /**
     * Method to notify the fragment
     * @param flag Some constant Flag
     * @param bundle Data Bundl
     */
    fun onNotifyFragment(flag: Int, bundle: Bundle)
}
