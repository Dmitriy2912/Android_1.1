package ru.netology.nmedia.viewmobel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post ()

class PostViewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
        //fun like(post: Post) = repository.like(post.id)
    val edited = MutableLiveData(empty)

    fun share(id: Long){
        repository.share(id)
    }

    fun likeById(id: Long) = repository.like(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun seveContent(content: String){
        edited.value?.let { post ->  val trimed: String = content.trim()

            if(post.content == trimed){
        repository.seve(post.copy(content = trimed))
            }
        }
        edited.value = empty

    }

}

