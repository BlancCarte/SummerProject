package com.example.summerproject.ui.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerId: String,
    val itemTitle: String,
    val key: Long,
    val key1 : String
){
    constructor():this("","","",0,"")
}