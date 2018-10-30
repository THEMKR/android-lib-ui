package com.nazgul.library.ui


/**
 * Created by A1ZFKXA3 on 1/24/2017.
 */

class ExceptionHandler : Thread.UncaughtExceptionHandler {
    private var mExceptionHandlerListener : ExceptionHandlerListener? = null

    /**
     * Constructor
     * @param exceptionHandlerListener
     */
    private constructor(exceptionHandlerListener : ExceptionHandlerListener?) {
        mExceptionHandlerListener = exceptionHandlerListener
    }

    override fun uncaughtException(thread : Thread, throwable : Throwable) {
        throwable.printStackTrace()
        mExceptionHandlerListener?.uncaughtException(throwable)
        System.exit(0)
    }

    /**
     * Callback to notifyTaskResponse the Exception
     */
    interface ExceptionHandlerListener {

        /**
         * Method to notifyTaskResponse the exception
         *
         * @param throwable
         */
        fun uncaughtException(throwable : Throwable)
    }

    companion object {

        /**
         * Method to attach the Exception Handler
         *
         * @param exceptionHandlerListener
         */
        fun attachExceptionHandler(exceptionHandlerListener : ExceptionHandlerListener) {
            Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler(exceptionHandlerListener))
        }
    }
}
