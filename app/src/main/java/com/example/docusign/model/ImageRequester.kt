package com.example.docusign.model

import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.example.docusign.controller.MainActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.content.DialogInterface
import kotlinx.android.synthetic.main.activity_main.*


class ImageRequester {
    companion object {
        val sURL =
            "https://pixabay.com/api/?key=&image_type=photo&q="

        fun getURL(query: String): String? {
            if (query != "") {
                val temp = sURL + query
                return temp
            }
            return null
        }
    }

    class JsonTask(context: MainActivity) : AsyncTask<String, String, String>() {
        var activity = context

        override fun doInBackground(vararg params: String): String? {
            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val stream: InputStream = connection.getInputStream()
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line = ""
                var nextLine = true

                while (nextLine) {
                    line = reader.readLine()
                    buffer.append(line + "\n");
                    if (reader.readLine() == null)
                        nextLine = false
                }
                return buffer.toString()

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if (result != null) {
                PhotoGenerator.parseJSON(result!!)
                if (PhotoGenerator.hitNumber > 0) {
                    MainActivity.adapter.notifyDataSetChanged()
                    this.activity.textView.text = ""
                } else {
                    this.activity.textView.text = "No results. Search again!"
                }

            } else {
                this.activity.textView.text = "You didn't search, search again!"
            }
        }
    }

}
