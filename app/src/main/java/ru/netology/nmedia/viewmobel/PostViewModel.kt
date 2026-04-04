package ru.netology.nmedia.viewmobel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = TODO(),
    author = TODO(),
    published = TODO(),
    content = TODO(),
    likes = TODO(),
    likedByMe = TODO(),
    shares = TODO(),
    sharedByMe = TODO(),
    context = TODO()
)

class PostViewModel : ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()

    //fun like(post: Post) = repository.like(post.id)
    val edited = MutableLiveData(empty)

    fun share(id: Long) {
        repository.share(id)
    }
    private val _editedPost = MutableStateFlow<Post>(Post.empty)
    val editedPost: StateFlow<Post> = _editedPost

    fun edit(post: Post) {
        _editedPost.value = post 
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
        edited.value = empty

    }




}

