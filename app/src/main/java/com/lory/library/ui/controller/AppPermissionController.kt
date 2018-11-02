package com.lory.library.ui.controller

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast


/**
 * Created by delhivery on 4/7/16.
 */
class AppPermissionController {
    private var mPermission : Array<String>? = null
    private var mActivity : Activity? = null
    private var mOnAppPermissionControllerListener : OnAppPermissionControllerListener? = null

    /**
     * Constructor
     *
     * @param activity
     * @param permission
     * @param onAppPermissionControllerListener
     */
    constructor(activity : Activity, permission : Array<String>, onAppPermissionControllerListener : OnAppPermissionControllerListener?) {
        mActivity = activity
        mOnAppPermissionControllerListener = onAppPermissionControllerListener
        if (permission != null) {
            mPermission = permission
        }else{
            mPermission = arrayOf()
        }
    }

    /**
     * Method to initialized the Application
     */
    fun initializedAppPermission() {
        if (isHaveAllRequiredPermission()) {
            mOnAppPermissionControllerListener?.onAppPermissionControllerListenerHaveAllRequiredPermission()
        } else {
            requestPermission()
        }
    }

    /**
     * Method to called from this method of Activity
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>?, grantResults : IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (! (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(mActivity, "App was unable to work properly. If required permission not granted", Toast.LENGTH_LONG).show()
                }
                initializedAppPermission()
            }
        }
    }

    /**
     * Method to know weather the App have All Required Permission
     *
     * @return TRUE if have all permission, else FALSE
     */
    private fun isHaveAllRequiredPermission() : Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in mPermission !!) {
                if (mActivity !!.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    /**
     * Method to know request the permission
     *
     * @return TRUE if have all permission, else FALSE
     */
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in mPermission !!) {
                if (mActivity !!.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    mActivity !!.requestPermissions(arrayOf(permission), REQUEST_PERMISSION)
                    return
                }
            }
        }
    }

    /**
     * Controller to notifyTaskResponse the App that the Database of this App is initialized
     */
    interface OnAppPermissionControllerListener {
        /**
         * Controller to notifyTaskResponse that App have all the required permission
         */
        fun onAppPermissionControllerListenerHaveAllRequiredPermission()
    }

    companion object {
        private val REQUEST_PERMISSION = 10000
    }
}
