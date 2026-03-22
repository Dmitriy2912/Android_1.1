package ru.netology.nmedia.viewmobel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like(post: Post) = repository.like(post.id)

    fun share(post: Post){
        repository.share(post.id)
    }

    fun likeById(id: Long) = repository.likeById(id)
}

