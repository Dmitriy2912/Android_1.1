package ru.netology.nmedia.dto

data class Post(
    var id: Long = 0,
    val author: String = "",
    val published: String = "",
    val content: String = "",
    val likes: Int = 0,
    val likedByMe: Boolean = false,
    val shares: Int = 0,
    val sharedByMe: Boolean = false,
    val context: String = ""
    // val likeById: Long = 0

) {
    companion object {
        val empty = Post()
    }
}