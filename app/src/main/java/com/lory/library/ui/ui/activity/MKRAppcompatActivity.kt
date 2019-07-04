package com.lory.library.ui.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lory.library.ui.callback.OnBaseActivityListener
import com.lory.library.ui.callback.OnBaseFragmentListener
import com.lory.library.ui.controller.AppPermissionController
import com.lory.library.ui.utils.Tracer

abstract class MKRAppcompatActivity : AppCompatActivity(), OnBaseActivityListener, AppPermissionController.OnAppPermissionControllerListener {

    companion object {
        const val TAG: String = "MKRAppcompatActivity"
    }

    private var appPermissionController: AppPermissionController? = null
    private var lastCallTime: Long = System.currentTimeMillis()
    private val PERMISSION_RESULT_THRASH_HOLD: Long = 250

    override fun onCreate(savedInstanceState: Bundle?) {
        //Tracer.LOG_ENABLE = true
        Tracer.debug(TAG, "onCreate: ")
        super.onCreate(savedInstanceState)
        setContentView(getActivityLayoutId())

        appPermissionController = AppPermissionController(this, getRequiredPermissions(), this)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        init(intent)
    }

    override fun onBackPressed() {
        Tracer.debug(TAG, "onBackPressed: ")
        var fragment: Fragment? = supportFragmentManager.findFragmentById(getDefaultFragmentContainerId())
        if (fragment != null && fragment is OnBaseFragmentListener && (fragment as OnBaseFragmentListener).onBackPressed()) {
            return
        }
        super.onBackPressed()
        fragment = supportFragmentManager.findFragmentById(getDefaultFragmentContainerId())
        if (fragment != null && fragment is OnBaseFragmentListener) {
            (fragment as OnBaseFragmentListener).onPopFromBackStack()
        }
        if (supportFragmentManager.backStackEntryCount <= 0) {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Tracer.debug(TAG, "onRequestPermissionsResult: ")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (lastCallTime + PERMISSION_RESULT_THRASH_HOLD > System.currentTimeMillis()) {
            openPermissionPage()
            finish()
            return
        }
        lastCallTime = System.currentTimeMillis()
        appPermissionController?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onAppPermissionControllerListenerHaveAllRequiredPermission() {
        Tracer.debug(TAG, "onAppPermissionControllerListenerHaveAllRequiredPermission: ")
        init(intent)
    }

    override fun onBaseActivityReplaceFragment(fragment: Fragment, bundle: Bundle?, tag: String) {
        onBaseActivityReplaceFragment(getDefaultFragmentContainerId(), fragment, bundle, tag)
    }

    override fun onBaseActivityReplaceFragment(containerId: Int, fragment: Fragment, bundle: Bundle?, tag: String) {
        Tracer.debug(TAG, "onBaseActivityReplaceFragment: $fragment : $tag")
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment, tag)
        if (bundle != null) {
            fragment.arguments = bundle
        }
        fragmentTransaction.commit()
    }

    override fun onBaseActivityAddFragment(fragment: Fragment, bundle: Bundle?, isAddToBackStack: Boolean, tag: String) {
        onBaseActivityAddFragment(getDefaultFragmentContainerId(), fragment, bundle, isAddToBackStack, tag)
    }

    override fun onBaseActivityAddFragment(containerId: Int, fragment: Fragment, bundle: Bundle?, isAddToBackStack: Boolean, tag: String) {
        Tracer.debug(TAG, "onBaseActivityAddFragment: $fragment : $tag")
        val findFragmentByTag = supportFragmentManager.findFragmentByTag(tag)
        if (findFragmentByTag == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.add(containerId, fragment, tag)
            if (isAddToBackStack) {
                fragmentTransaction.addToBackStack(tag)
            }
            if (bundle != null) {
                fragment.arguments = bundle
            }
            fragmentTransaction.commit()
        } else {
            while (true) {
                var fragment: Fragment = supportFragmentManager.findFragmentById(containerId)
                    ?: break
                if ((fragment.tag ?: "").equals(tag)) {
                    if (bundle != null) {
                        findFragmentByTag.arguments = bundle
                    }
                    if (fragment is OnBaseFragmentListener) {
                        fragment.onPopFromBackStack()
                    }
                    break
                }
                supportFragmentManager.popBackStackImmediate()
            }
        }
    }

    override fun onBaseActivitySetScreenTitle(title: String) {
        Tracer.debug(TAG, "onBaseActivitySetScreenTitle: $title")
    }

    override fun onBaseActivitySetToolbar(toolbarLayout: View) {
        Tracer.debug(TAG, "onBaseActivitySetToolbar: $toolbarLayout")
    }

    /**
     * Method called after user allowed all the required permission. User must write all its required code here
     * @param intent
     */
    abstract fun init(intent: Intent?)

    /**
     * Method to get the ID of the Default Fragment Container.
     * This is used by [onBackPressed] so make sure in case of backpress call user should return the appropriate container id
     */
    abstract fun getDefaultFragmentContainerId(): Int

    /**
     * Method to get the ID of the Activity Layout
     */
    abstract fun getActivityLayoutId(): Int

    /**
     * Method to get the array of the required permission
     */
    abstract fun getRequiredPermissions(): Array<String>

    /**
     * Method to check weather the user have desired permission. If have all then call init(), else call permission
     */
    fun checkAndCallPermission() {
        Tracer.debug(TAG, "checkAndCallPermission: ${appPermissionController?.isHaveAllRequiredPermission()}")
        if (appPermissionController?.isHaveAllRequiredPermission() == true) {
            init(intent)
        } else {
            appPermissionController?.requestPermission()
        }
    }

    /**
     * Method to open the permission age
     */
    private fun openPermissionPage() {
        Tracer.debug(TAG, "openPermissionPage: ")
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}