package com.example.simply_watered_kotlin2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simply_watered_kotlin2.Adapters.DevicesAdapter
import com.example.simply_watered_kotlin2.Adapters.UsersAdapter
import com.example.simply_watered_kotlin2.Models.DeviceModel
import com.example.simply_watered_kotlin2.Models.UserModel
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_devices.*
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.fragment_users.button
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DevicesFragment : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: DevicesAdapter

    var resultView: TextView? = null

    companion object {
        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }

    //3
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_devices, container, false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?){
        super.onActivityCreated(savedInstanceState)


        recyclerDeviceList.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(getContext())
        recyclerDeviceList.layoutManager = layoutManager

//        val buttonView: Button = requireActivity()!!.findViewById<View>(R.id.button) as Button
        button.setOnClickListener {
            GlobalScope.async {
                getDevices()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            getDevices()
        }

        goToUsersButton.setOnClickListener{
            findNavController().navigate(R.id.action_devicesFragment_to_usersFragment2)
        }

    }

    public suspend fun getDevices() {
        try {
            val result = GlobalScope.async {
                callApi("http://10.0.2.2:5000/api/adminDevices")
            }.await()

            onResponse(result)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun callApi(apiUrl:String ):String?{
        var result: String? = ""
        val url: URL;
        var connection: HttpURLConnection? = null
        try {
            url = URL(apiUrl)
            connection = url.openConnection() as HttpURLConnection
            // set headers for the request
            // set host name
//            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
//
//            // set the rapid-api key
//            connection.setRequestProperty("x-rapidapi-key", "<YOUR_RAPIDAPI_KEY>")
//            connection.setRequestProperty("content-type", "application/x-www-form-urlencoded")
            // set the request method - POST

//            val connection = URL("http://10.0.2.2:5000/api/regiongroups").openConnection() as HttpURLConnection
//            val data1 = connection.inputStream.bufferedReader().readText()


            connection.requestMethod = "GET"
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
    private fun onResponse(result: String?) {
        try {
            val builder = GsonBuilder()
            val gson = builder.create()


            val resultJson: Array<DeviceModel> = gson.fromJson(result, Array<DeviceModel>::class.java)

            adapter = DevicesAdapter(resultJson)
            adapter.notifyDataSetChanged()
            getActivity()?.runOnUiThread { recyclerDeviceList.adapter = adapter }


//
//            // convert the string to JSON object for better reading
//            val resultJson = JSONObject(result)
//
//            // Initialize prediction text
//            var prediction ="Today's prediction nn"
//
//
//            // Update text with various fields from response
//            prediction += resultJson.getString("date_range")+"nn"
//            prediction += resultJson.getString("description")
//
//            //Update the prediction to the view
//            setText(this.resultView,prediction)

        } catch (e: Exception) {
            e.printStackTrace()
            this.resultView!!.text = "Oops!! something went wrong, please try again"
        }
    }

//    private suspend fun putDevices(deviceId: String) {
//        try {
//
//            val result = GlobalScope.async {
//                callApiPut("http://10.0.2.2:5000/api/admindevices",deviceId)
//            }.await()
//
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//    private fun callApiPut(apiUrl:String, deviceId:String ):String?{
//        var result: String? = ""
//        val url: URL;
//        var connection: HttpURLConnection? = null
//        try {
//            url = URL("$apiUrl/$deviceId")
//            connection = url.openConnection() as HttpURLConnection
////                connection.setRequestProperty("content-type", "application/json; utf-8")
//            // set headers for the request
//            // set host name
////            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
////
////            // set the rapid-api key
////            connection.setRequestProperty("x-rapidapi-key", "<YOUR_RAPIDAPI_KEY>")
//
//            // set the request method - POST
//
////            val connection = URL("http://10.0.2.2:5000/api/regiongroups").openConnection() as HttpURLConnection
////            val data1 = connection.inputStream.bufferedReader().readText()
//
//
//            connection.requestMethod = "PUT"
//            val `in` = connection.inputStream
//            val reader = InputStreamReader(`in`)
//
//            // read the response data
//            var data = reader.read()
//            while (data != -1) {
//                val current = data.toChar()
//                result += current
//                data = reader.read()
//            }
//            return result
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        // if not able to retrieve data return null
//        return null
//
//    }


}