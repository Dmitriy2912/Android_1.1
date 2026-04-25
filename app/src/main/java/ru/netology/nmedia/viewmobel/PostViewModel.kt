package ru.netology.nmedia.viewmobel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepositorySharedFileImpl


private val empty = Post(
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepositorySharedFileImpl =
        PostRepositorySharedFileImpl(application)
    val data = repository.get()
    private val _edited = MutableLiveData(empty)
    val edited: LiveData<Post> = _edited

    fun edit(post: Post) {
        _edited.value = post
    }
    fun  clearEdit() {
        _edited.value = empty
    }

    //fun like(post: Post) = repository.like(post.id)


    fun share(id: Long) {
        repository.share(id)
    }







    fun likeById(id: Long) = repository.like(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun seveContent(content: String) {
         edited.value?.let { post ->
            val trimed: String = content.trim()

            if (post.content != trimed) {
                repository.seve(post.copy(content = trimed))
            }
        }
        _edited.value = empty

    }




}

