package com.mkrworld.androidlibui.utils

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
        fun getMoreApps(context: Context) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:MKR WORLD"))
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * Method top send feedback
         *
         * @param context
         * @param appName
         */
        fun sendFeedback(context: Context, appName: String) {
            try {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mkrworldapps@gmail.com"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
                emailIntent.putExtra(Intent.EXTRA_TEXT, "")
                emailIntent.type = "message/rfc822"
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(emailIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * Method to share application
         *
         * @param context
         * @param appName
         * @param shareMessage
         */
        @JvmOverloads
        fun shareApp(context: Context, appName: String, shareMessage: String) {
            shareApp(context, appName, shareMessage, "")
        }

        /**
         * Method to share application
         *
         * @param context
         * @param appName
         * @param shareMessage
         * @param referrer
         */
        @JvmOverloads
        fun shareApp(context: Context, appName: String, shareMessage: String, referrer: String) {
            shareApp(context, appName, shareMessage, "", "http://play.google.com/store/apps/details?id=" + context.packageName, referrer)
        }

        /**
         * Method to share application
         *
         * @param context
         * @param appName
         * @param shareMessage
         * @param shareUrl
         * @param referrer
         */
        @JvmOverloads
        fun shareApp(context: Context, appName: String, shareMessage: String, infoText: String, shareUrl: String, referrer: String) {
            var shareUrl: String = shareUrl + if (referrer != null && !referrer.trim().isEmpty()) {
                "&referrer=$referrer"
            } else {
                ""
            }
            val message = shareMessage + "\n\n\n\n" + shareUrl + if (infoText != null && !infoText.trim { it <= ' ' }.isEmpty()) {
                "\n\n\n" + infoText
            } else {
                ""
            }
            val sentIntent = Intent(Intent.ACTION_SEND)
            sentIntent.type = "text/plain"
            sentIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
            sentIntent.putExtra(Intent.EXTRA_TEXT, message)
            val sendIntent = Intent.createChooser(sentIntent, "Share File")
            sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(sendIntent)
        }
    }
}