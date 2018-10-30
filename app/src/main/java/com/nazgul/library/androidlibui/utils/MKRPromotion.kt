package com.nazgul.library.androidlibui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class MKRPromotion {
    companion object {

        /**
         * Method to send review
         *
         * @param context
         */
        fun sendReview(context: Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * Method to open downloading
         *
         * @param context
         * @param url
         */
        fun openDownloadUrl(context: Context, url: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }


        /**
         * Method to get more apps
         *
         * @param context
         */
        fun openMoreAppScreen(context: Context) {
            openMoreAppScreen(context, "MKR WORLD")
        }

        /**
         * Method to get more apps
         *
         * @param context
         * @param pub
         */
        fun openMoreAppScreen(context: Context, pub: String) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:${pub}"))
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        /**
         * Method to get the share Intent
         * @param appName
         * @param shareMessage
         * @param shareUrl
         */
        @JvmOverloads
        fun getShareIntent(appName: String, shareMessage: String, shareUrl: String): Intent {
            val message = shareMessage + "\n\n" + shareUrl
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, appName)
            intent.putExtra(Intent.EXTRA_TEXT, message)
            val sendIntent = Intent.createChooser(intent, "Share")
            sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }

        /**
         * Method to get the URL used at the Time of Sharing App
         * @param context
         * @return http://play.google.com/store/apps/details?id=pkg
         */
        fun getShareAppUrl(context: Context): String {
            return "http://play.google.com/store/apps/details?id=${context.packageName}"
        }

        /**
         * Method to get the URL used at the Time of Sharing App
         * @param context
         * @param referrer
         * @return http://play.google.com/store/apps/details?id=pkg&referrer=ref
         */
        fun getShareAppUrl(context: Context, referrer: String): String {
            return getShareAppUrl(context) + if (referrer != null && !referrer.trim().isEmpty()) {
                "&referrer=$referrer"
            } else {
                ""
            }
        }
    }
}