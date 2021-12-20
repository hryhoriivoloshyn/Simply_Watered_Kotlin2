package com.example.simply_watered_kotlin2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simply_watered_kotlin2.Models.UserModel
import com.example.simply_watered_kotlin2.R

import kotlinx.android.synthetic.main.users_layout.view.*

class UsersAdapter(private val dataSet: Array<UserModel>) :
    RecyclerView.Adapter<UsersAdapter.ViewHolder>() {



        /**
         * Provide a reference to the type of views that you are using
         * (custom ViewHolder).
         */
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView

            init {
                // Define click listener for the ViewHolder's View.
                textView = itemView.findViewById(R.id.txt_email)
            }
        }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.users_layout, viewGroup, false)

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            // Get element from your dataset at this position and replace the
            // contents of the view with that element
            viewHolder.textView.text = dataSet[position].email
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = dataSet.size

    }

