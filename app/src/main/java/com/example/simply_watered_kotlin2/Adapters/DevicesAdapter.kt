package com.example.simply_watered_kotlin2.Adapters

import android.media.midi.MidiManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simply_watered_kotlin2.CallApi.DevicesCall
import com.example.simply_watered_kotlin2.Models.Device
import com.example.simply_watered_kotlin2.Models.DeviceModel
import com.example.simply_watered_kotlin2.Models.UserModel
import com.example.simply_watered_kotlin2.R
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class DevicesAdapter(private val dataSet: Array<DeviceModel>) :
    RecyclerView.Adapter<DevicesAdapter.ViewHolder>() {



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView
        val textSerialNumber: TextView
        val deviceId: TextView
        val button: Button

        init {
            // Define click listener for the ViewHolder's View.
            textName = itemView.findViewById(R.id.txt_name)
            textSerialNumber=itemView.findViewById(R.id.txt_serialNumber)
            deviceId=itemView.findViewById(R.id.txt_deviceId)
            button=itemView.findViewById(R.id.disableDeviceButton)

            button.setOnClickListener {
                GlobalScope.async {
                    putDevices(deviceId.text.toString())
                }
            }
        }

        private suspend fun putDevices(deviceId: String) {
            try {

                val result = GlobalScope.async {
                    callApiPut("http://10.0.2.2:5000/api/admindevices",deviceId)
                }.await()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        private fun callApiPut(apiUrl:String, deviceId:String ):String?{
            var result: String? = ""
            val url: URL;
            var connection: HttpURLConnection? = null
            try {
                url = URL("$apiUrl/$deviceId")
                connection = url.openConnection() as HttpURLConnection
//                connection.setRequestProperty("content-type", "application/json; utf-8")
                // set headers for the request
                // set host name
//            connection.setRequestProperty("x-rapidapi-host", "sameer-kumar-aztro-v1.p.rapidapi.com")
//
//            // set the rapid-api key
//            connection.setRequestProperty("x-rapidapi-key", "<YOUR_RAPIDAPI_KEY>")

                // set the request method - POST

//            val connection = URL("http://10.0.2.2:5000/api/regiongroups").openConnection() as HttpURLConnection
//            val data1 = connection.inputStream.bufferedReader().readText()


                connection.requestMethod = "PUT"
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


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.devices_layout, viewGroup, false)


        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textName.text = dataSet[position].deviceType.deviceName
        viewHolder.textSerialNumber.text = dataSet[position].serialNumber
        viewHolder.deviceId.text=dataSet[position].deviceId.toString()
       if(dataSet[position].active){
           viewHolder.button.text="Вимкнути"
       }else{
           viewHolder.button.text="Увімкнути"
       }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



}

