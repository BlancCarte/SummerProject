package com.example.summerproject

data class ArticleModel(
    val sellerEmail: String,
    val title: String,
    val createdAt: Long,
    val price: String,
    val imageUrl: String,
    val content: String,
    val nickname: String,
    val sellerId: String
) {
    // 파이어베이스에 클래스 단위로 올리려면 인자빈생성자 필요;
    constructor() : this("", "", 0, "", "", "", "", "")
}