package ru.netology.nmedia.viewmobel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun like(post: Post) = repository.like(post.id)

    fun share(id: Long){
        repository.share(id)
    }

    fun likeById(id: Post) = repository.likeById(id)

}

