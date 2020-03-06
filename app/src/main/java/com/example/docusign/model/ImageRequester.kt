package com.example.docusign.model

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


class ImageRequester{
    companion object{
        val sURL = "https://pixabay.com/api/?key=[redacted]&image_type=photo&q=toronto"
        val isLoadingData = false
    }

    fun getURL(query:String): String{
        var temp = sURL+query
        return temp
    }
    class JsonTask(context: Context) : AsyncTask<String, String, String>() {
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
                    Log.d("Response: ", "> $line")

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
            PhotoGenerator.parseJSON(result!!)
            MainActivity.adapter.notifyDataSetChanged()
        }
    }

}
