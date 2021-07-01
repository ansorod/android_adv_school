package com.ansorod.chat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ansorod.chat.R
import com.ansorod.chat.presentation.uimodel.UIMessage
import java.text.SimpleDateFormat

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var messageList: List<UIMessage>? = null

    fun setData(data: List<UIMessage>?) {
        messageList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        messageList?.let { list ->
            val current = list[position]
            holder.userName.text = current.userName
            holder.content.text = current.message

            val format = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            holder.timestamp.text = format.format(current.timestamp)

        }
    }

    override fun getItemCount(): Int {
        return messageList?.size ?: 0
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userName = view.findViewById<TextView>(R.id.userNameTextView)
        val content = view.findViewById<TextView>(R.id.contentTextView)
        val timestamp = view.findViewById<TextView>(R.id.timestampTextView)
    }
}