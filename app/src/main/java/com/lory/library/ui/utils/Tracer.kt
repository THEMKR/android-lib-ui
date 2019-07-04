package com.lory.library.ui.utils

import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.lory.library.ui.R
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * Created by delhivery on 21/3/16.
 */
class Tracer {
    companion object {
        var LOG_ENABLE = false

        /**
         * Method to print debug log<br></br>
         * [Log] Information
         *
         * @param TAG
         * @param message
         */
        fun debug(TAG: String, message: String) {
            if (LOG_ENABLE) {
                Log.d(TAG, message)
            }
        }

        /**
         * Method to write data in Disk
         *
         * @param TAG
         * @param num
         * @param caller
         * @param data
         */
        fun writeOnDisk(TAG: String, num: Int, caller: String, data: String) {
            if (LOG_ENABLE) {
                val fileDir = File(Environment.getExternalStorageDirectory(), "CTA")
                if (!fileDir.exists()) {
                    fileDir.mkdir()
                }
                val file = File(fileDir.absolutePath, "" + caller + "_" + num + "_" + System.currentTimeMillis() + ".txt")
                if (!file.exists()) {
                    try {
                        file.createNewFile()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
                var bufferedWriter: BufferedWriter? = null
                try {
                    bufferedWriter = BufferedWriter(FileWriter(file))
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.write(data)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    try {
                        bufferedWriter.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        /**
         * Method to print error log<br></br>
         * [Log] Error
         *
         * @param TAG
         * @param message
         */
        fun error(TAG: String, message: String) {
            if (LOG_ENABLE) {
                Log.e(TAG, message)
            }
        }

        /**
         * Method to print information log<br></br>
         * [Log] Debug
         *
         * @param TAG
         * @param message
         */
        fun info(TAG: String, message: String) {
            if (LOG_ENABLE) {
                Log.i(TAG, message)
            }
        }

        /**
         * Show TOAST<br></br>
         *
         * @param context activity context.
         * @param text    The text to show.  Can be formatted text.
         */
        fun showToast(context: Context, text: String) {
            val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0, 0)
            toast.show()
        }

        /**
         * Show SNACK<br></br>
         *
         * @param view The view to find NewsAdapterItemHandler mParent from.
         * @param text The text to show.  Can be formatted text.
         */
        fun showSuccessSnack(view: View, text: String) {
            val snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            snack.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.snack_success))
            snack.setActionTextColor(Color.WHITE)
            snack.show()
        }

        /**
         * Show SNACK<br></br>
         *
         * @param view The view to find NewsAdapterItemHandler mParent from.
         * @param text The text to show.  Can be formatted text.
         */
        fun showSnack(view: View, text: String) {
            Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
        }

        /**
         * Show SNACK<br></br>
         *
         * @param view      The view to find NewsAdapterItemHandler mParent from.
         * @param textResId The text Res Id to show.
         */
        fun showSnack(view: View, textResId: Int) {
            Snackbar.make(view, textResId, Snackbar.LENGTH_LONG).show()
        }
    }
}
