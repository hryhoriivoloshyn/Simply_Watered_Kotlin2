package com.example.simply_watered_kotlin2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.*
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.simply_watered_kotlin2.Models.DeviceModel
import com.example.simply_watered_kotlin2.Models.UserOutputModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.add_user_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.BufferedReader
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class AddUserFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.add_user_fragment, container, false);


    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)
        btn_send.setOnClickListener{
            GlobalScope.async {
                sendUser()
            }
        }
        btn_return.setOnClickListener{
            findNavController().navigate(R.id.action_addUserFragment_to_usersFragment)
        }

    }

    private suspend fun sendUser() {
        try {
            val result = GlobalScope.async {
                callApi("http://10.0.2.2:5000/api/admin/users")
            }.await()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callApi(apiUrl:String ){
        var result: String? = ""
        val url: URL;
        var connection: HttpURLConnection? = null
        try {
            url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection


            var message: UserOutputModel= UserOutputModel(edit_email.text.toString(),edit_password.text.toString())

            val builder = GsonBuilder()
            val gson = builder.create()

            val resultJson=gson.toJson(message)
            val postData: ByteArray = resultJson.toByteArray(StandardCharsets.UTF_8)

            connection.setRequestProperty("charset", "utf-8")
            connection.setRequestProperty("Content-length", postData.size.toString())
            connection.setRequestProperty("Content-Type", "application/json")
            connection.requestMethod = "POST"
            val outputStream: DataOutputStream = DataOutputStream(connection.outputStream)
            outputStream.write(postData)
            outputStream.flush()

            // read the response data

            if (connection.responseCode != HttpURLConnection.HTTP_OK && connection.responseCode != HttpURLConnection.HTTP_CREATED) {
                try {
                    val inputStream: DataInputStream = DataInputStream(connection.inputStream)
                    val reader: BufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val output: String = reader.readLine()

                    println("There was error while connecting the chat $output")
                    System.exit(0)

                } catch (exception: Exception) {
                    throw Exception("Exception while push the notification  $exception.message")
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }



    }
}