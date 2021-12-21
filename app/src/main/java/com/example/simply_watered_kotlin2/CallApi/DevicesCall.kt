package com.example.simply_watered_kotlin2.CallApi

import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

 class DevicesCall {
    private fun callApiPost(apiUrl:String, serialNumber:String ):String?{
        var result: String? = ""
        val url: URL;
        var connection: HttpURLConnection? = null
        try {
            url = URL("$apiUrl/$serialNumber")
            connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("content-type", "application/json; utf-8")
            // set headers for the request
            // set host name
//            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
//
//            // set the rapid-api key
//            connection.setRequestProperty("x-rapidapi-key", "<YOUR_RAPIDAPI_KEY>")

            // set the request method - POST

//            val connection = URL("http://10.0.2.2:5000/api/regiongroups").openConnection() as HttpURLConnection
//            val data1 = connection.inputStream.bufferedReader().readText()


            connection.requestMethod = "POST"
            val `in` = connection.inputStream
            val reader = InputStreamReader(`in`)

            // read the response data
            var data = reader.read()
            while (data != -1) {
                val current = data.toChar()
                result += current
                data = reader.read()
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // if not able to retrieve data return null
        return null

    }
}