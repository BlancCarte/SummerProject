package com.example.summerproject.ui.chatdetail

data class ChatItem(
    val senderId: String,
    val message: String,
    val senderNickname: String,
){
    constructor(): this("","","")
}