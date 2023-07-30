package com.groupthree.blocklink

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomChatAdapter(private val messages: List<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MessageType.INCOMING.ordinal -> {
                val view = inflater.inflate(R.layout.item_message_incoming, parent, false)
                IncomingViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.item_message_outgoing, parent, false)
                OutgoingViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int): Int =
        messages[position].messageType.ordinal

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when (holder) {
            is IncomingViewHolder -> {
                holder.tvSenderName.text = message.senderName
                holder.tvDateInfo.text = message.dateInfo
                holder.tvMessageText.text = message.messageText
            }
            is OutgoingViewHolder -> {
                holder.tvSenderName.text = message.senderName
                holder.tvDateInfo.text = message.dateInfo
                holder.tvMessageText.text = message.messageText
            }
        }
    }

    // ViewHolder for incoming messages
    class IncomingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSenderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val tvDateInfo: TextView = itemView.findViewById(R.id.tvDateInfo)
        val tvMessageText: TextView = itemView.findViewById(R.id.tvMessageText)
    }

    // ViewHolder for outgoing messages
    class OutgoingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSenderName: TextView = itemView.findViewById(R.id.tvSenderName)
        val tvDateInfo: TextView = itemView.findViewById(R.id.tvDateInfo)
        val tvMessageText: TextView = itemView.findViewById(R.id.tvMessageText)
    }
}
