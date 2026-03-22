package ru.netology.nmedia.dto

data class Post (
    var id: Long,
    val author: String,
    val published: String,
    val content: String,
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shares: Int,
    val sharedByMe: Boolean = false,
    val likeById: Long

) {


}