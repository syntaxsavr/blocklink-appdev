package com.groupthree.blocklink

data class Message(
    val senderName: String,
    val dateInfo: String,
    val messageText: String,
    val messageType: MessageType
)

enum class MessageType {
    INCOMING,
    OUTGOING
}