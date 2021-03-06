package com.mkr.lib.ui.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.mkr.lib.ui.ui.view.ProgressView


/**
 * @author THEMKR
 * Dialog class to handle the Default Utility
 */
class MKRDialogUtil {
    companion object {
        private var alertDialog: Dialog? = null

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param okText
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showOKDialog(context: Context, iconId: Int, title: String, message: String, okText: String, onOkClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog = null
            }
            val alertDialog: AlertDialog.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialog = AlertDialog.Builder(context, Settings.getDialogTheme(context))
            } else {
                alertDialog = AlertDialog.Builder(context)
            }
            alertDialog.setIcon(iconId)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setPositiveButton(okText) { dialog, which ->
                dialog.dismiss()
                onOkClickListener?.onClick(dialog, which)
            }
            alertDialog.setCancelable(cancellable)
            val dialog = alertDialog.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            return dialog
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param  layoutId
         * @param titleTextViewId
         * @param title
         * @param message
         * @param messageTextViewId
         * @param okTextViewId
         * @param okText
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showCustomOKDialog(context: Context, layoutId: Int, titleTextViewId: Int, title: String, messageTextViewId: Int, message: String, okTextViewId: Int, okText: String, onOkClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog = null
            }
            var customDialog: CustomDialog
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customDialog = CustomDialog(context, Settings.getDialogTheme(context), layoutId)
            } else {
                customDialog = CustomDialog(context, layoutId)
            }
            customDialog.titleTextViewId = titleTextViewId
            customDialog.title = title
            customDialog.messageTextViewId = messageTextViewId
            customDialog.message = message
            customDialog.positiveButtonId = okTextViewId
            customDialog.positiveText = okText
            customDialog.onOkClickListener = onOkClickListener
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            customDialog.setCancelable(cancellable)
            alertDialog = customDialog
            alertDialog!!.show()
            return alertDialog!!
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param okText
         * @param onOkClickListener
         * @param cancelText
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showOKCancelDialog(context: Context, iconId: Int, title: String, message: String, okText: String, onOkClickListener: DialogInterface.OnClickListener?, cancelText: String, onCancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog = null
            }
            val alertDialog: AlertDialog.Builder
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                alertDialog = AlertDialog.Builder(context, Settings.getDialogTheme(context))
            } else {
                alertDialog = AlertDialog.Builder(context)
            }
            alertDialog.setIcon(iconId)
            alertDialog.setTitle(title)
            alertDialog.setMessage(message)
            alertDialog.setPositiveButton(okText) { dialog, which ->
                dialog.dismiss()
                onOkClickListener?.onClick(dialog, which)
            }
            alertDialog.setNegativeButton(cancelText) { dialog, which ->
                dialog.dismiss()
                onCancelClickListener?.onClick(dialog, which)
            }
            alertDialog.setCancelable(cancellable)
            val dialog = alertDialog.create()
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.show()
            return dialog
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param  layoutId
         * @param titleTextViewId
         * @param title
         * @param message
         * @param messageTextViewId
         * @param okTextViewId
         * @param okText
         * @param onOkClickListener
         * @param cancelTextViewId
         * @param cancelText
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showCustomOKCancelDialog(context: Context, layoutId: Int, titleTextViewId: Int, title: String, messageTextViewId: Int, message: String, okTextViewId: Int, okText: String, onOkClickListener: DialogInterface.OnClickListener?, cancelTextViewId: Int, cancelText: String, onCancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog = null
            }
            var customDialog: CustomDialog
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                customDialog = CustomDialog(context, Settings.getDialogTheme(context), layoutId)
            } else {
                customDialog = CustomDialog(context, layoutId)
            }
            customDialog.titleTextViewId = titleTextViewId
            customDialog.title = title
            customDialog.messageTextViewId = messageTextViewId
            customDialog.message = message
            customDialog.positiveButtonId = okTextViewId
            customDialog.positiveText = okText
            customDialog.onOkClickListener = onOkClickListener
            customDialog.negativeButtonId = cancelTextViewId
            customDialog.negativeText = cancelText
            customDialog.onCancelClickListener = onCancelClickListener
            customDialog.setCancelable(cancellable)
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            alertDialog = customDialog
            alertDialog!!.show()
            return alertDialog!!
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showOKDialog(context: Context, iconId: Int, title: String, message: String, onOkClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            return showOKDialog(context, iconId, title, message, "OK", onOkClickListener, cancellable)
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param  layoutId
         * @param titleTextViewId
         * @param title
         * @param message
         * @param messageTextViewId
         * @param okTextViewId
         * @param onOkClickListener
         * @param cancellable
         * @return
         */
        fun showCustomOKDialog(context: Context, layoutId: Int, titleTextViewId: Int, title: String, messageTextViewId: Int, message: String, okTextViewId: Int, onOkClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            return showCustomOKDialog(context, layoutId, titleTextViewId, title, messageTextViewId, message, okTextViewId, "OK", onOkClickListener, cancellable)
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param iconId
         * @param title
         * @param message
         * @param onOkClickListener
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showOKCancelDialog(context: Context, iconId: Int, title: String, message: String, onOkClickListener: DialogInterface.OnClickListener?, onCancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            return showOKCancelDialog(context, iconId, title, message, "OK", onOkClickListener, "CANCEL", onCancelClickListener, cancellable)
        }

        /**
         * Method to show Ok Dialog, Dialog dismiss on button click
         *
         * @param context
         * @param  layoutId
         * @param titleTextViewId
         * @param title
         * @param message
         * @param messageTextViewId
         * @param okTextViewId
         * @param okText
         * @param onOkClickListener
         * @param cancelTextViewId
         * @param cancelText
         * @param onCancelClickListener
         * @param cancellable
         * @return
         */
        fun showCustomOKCancelDialog(context: Context, layoutId: Int, titleTextViewId: Int, title: String, messageTextViewId: Int, message: String, okTextViewId: Int, onOkClickListener: DialogInterface.OnClickListener?, cancelTextViewId: Int, onCancelClickListener: DialogInterface.OnClickListener?, cancellable: Boolean): Dialog {
            return showCustomOKCancelDialog(context, layoutId, titleTextViewId, title, messageTextViewId, message, okTextViewId, "OK", onOkClickListener, cancelTextViewId, "CANCEL", onCancelClickListener, cancellable)
        }

        /***
         * To dismiss the dialog
         */
        fun dismissLoadingDialog() {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
                alertDialog!!.cancel()
            }
            alertDialog = null
        }

        /**
         * Method to show the Default Loading Dialog
         * @param context
         * @param text
         */
        fun showLoadingDialog(context: Context, text: String?) {
            if (alertDialog != null && alertDialog!!.isShowing) {
                return
            }
            alertDialog = Dialog(context)
            alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val relativeLayout = RelativeLayout(context)
            val paddingBottom = (context.resources.displayMetrics.heightPixels.toFloat() * 0.2f).toInt()
            relativeLayout.setPadding(0, 0, 0, paddingBottom)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            relativeLayout.layoutParams = layoutParams
            val progressView = ProgressView(context)
            progressView.mTextMessage = text ?: "PROCESSING....."
            val relativeLayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            relativeLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            relativeLayout.addView(progressView, relativeLayoutParams)
            alertDialog!!.setContentView(relativeLayout)
            alertDialog!!.setCanceledOnTouchOutside(false)
            alertDialog!!.setCancelable(false)
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            alertDialog!!.show()
        }

        /**
         * Method to show the custom Loading Dialog
         * @param context
         * @param view
         */
        fun showLoadingDialog(context: Context, view: View) {
            if (alertDialog != null && alertDialog!!.isShowing) {
                return
            }
            alertDialog = Dialog(context)
            alertDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val relativeLayout = RelativeLayout(context)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            relativeLayout.layoutParams = layoutParams
            val childParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)
            childParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            relativeLayout.addView(view, childParams)
            alertDialog!!.setContentView(relativeLayout)
            alertDialog!!.setCanceledOnTouchOutside(false)
            alertDialog!!.setCancelable(false)
            alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            alertDialog!!.show()
        }
    }

    /**
     * Class to show the Custom Dialog
     */
    private class CustomDialog : Dialog, View.OnClickListener {

        private val layoutId: Int
        var titleTextViewId: Int = -1
        var title: String = ""
        var messageTextViewId: Int = -1
        var message: String = ""
        var positiveButtonId: Int = -1
        var positiveText: String = ""
        var negativeButtonId: Int = -1
        var negativeText: String = ""
        var onOkClickListener: DialogInterface.OnClickListener? = null
        var onCancelClickListener: DialogInterface.OnClickListener? = null

        constructor(context: Context, layoutId: Int) : super(context) {
            this.layoutId = layoutId
        }

        constructor(context: Context, themeResId: Int, layoutId: Int) : super(context, themeResId) {
            this.layoutId = layoutId
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(layoutId)
        }

        override fun onStart() {
            super.onStart()
            if (titleTextViewId != -1) {
                val textView = findViewById<TextView>(titleTextViewId)
                textView.text = title
                textView.visibility = if (textView.text.toString().trim().isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            if (messageTextViewId != -1) {
                val textView = findViewById<TextView>(messageTextViewId)
                textView.text = message
                textView.visibility = if (textView.text.toString().trim().isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            if (positiveButtonId != -1) {
                val textView = findViewById<TextView>(positiveButtonId)
                textView.setOnClickListener(this)
                textView.text = positiveText
                textView.visibility = if (textView.text.toString().trim().isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
            if (negativeButtonId != -1) {
                val textView = findViewById<TextView>(negativeButtonId)
                textView.setOnClickListener(this)
                textView.text = negativeText
                textView.visibility = if (textView.text.toString().trim().isNotEmpty()) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                positiveButtonId -> {
                    dismiss()
                    onOkClickListener?.onClick(this, positiveButtonId)
                }
                negativeButtonId -> {
                    dismiss()
                    onCancelClickListener?.onClick(this, negativeButtonId)
                }
            }
        }
    }

    /**
     * Class to hold the setting of Dialog
     */
    class Settings {
        companion object {

            private val STORE = "MKRDIALOG"
            private val THEME = "THEME"

            /**
             * Method to get the Theme of the Dialog
             * @param context
             */
            fun getDialogTheme(context: Context): Int {
                return getShearedPreference(context).getInt(THEME, android.R.style.Theme_Material_Light_Dialog_Alert)
            }

            /**
             * Method to set the Theme of the Dialog
             * @param context
             * @param theme
             */
            fun setDialogTheme(context: Context, theme: Int): Int {
                return getShearedPreference(context).getInt(THEME, theme)
            }

            /**
             * Method to clear the Data Store
             *
             * @param context
             */
            fun clearStore(context: Context) {
                getShearedPreferenceEditor(context).clear().commit()
            }

            /**
             * Method to return the Data Store Preference
             *
             * @param context
             * @return
             */
            private fun getShearedPreference(context: Context): SharedPreferences {
                return context.getSharedPreferences(STORE, Context.MODE_PRIVATE)
            }

            /**
             * caller to commit this editor
             *
             * @param context
             * @return Editor
             */
            private fun getShearedPreferenceEditor(context: Context): SharedPreferences.Editor {
                return getShearedPreference(context).edit()
            }

        }
    }
}
