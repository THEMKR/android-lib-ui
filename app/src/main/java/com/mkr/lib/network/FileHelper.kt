package com.mkr.lib.network

import android.content.Context
import android.text.TextUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * @author THEMKR
 */
class FileHelper {

    companion object {

        fun getAssetFileJson(context: Context, fileName: String): JSONObject? {
            if (TextUtils.isEmpty(fileName)) return null
            try {
                return parseInputStreamToJson(InputStreamReader(context.assets.open(fileName)))
            } catch (e: IOException) {
            }

            return null
        }

        /**
         * Method to parse the Input stream to the Json
         *
         * @param isr
         * @return
         */
        internal fun parseInputStreamToJson(isr: InputStreamReader?): JSONObject? {
            var data: JSONObject? = null
            if (isr == null) return data

            var reader: BufferedReader? = null
            try {
                reader = BufferedReader(isr)
                var line: String
                val builder = StringBuilder()
                while (true) {
                    line = reader.readLine()
                    if (line == null) {
                        break
                    }
                    builder.append(line)
                }
                try {
                    data = JSONObject(builder.toString())
                } catch (e: JSONException) {
                }

                return data

            } catch (e: IOException) {
                return data

            } finally {
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                    }

                }
            }
        }
    }
}