package ru.netology.nmedia.repository

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post
import java.io.File
import java.lang.reflect.Type


class PostRepositorySharedFileImpl(private val context: Context) : PostRepository {

    //private val prefs = context.getSharedPreferences("posts", MODE_PRIVATE)
    private val gson = Gson()

    //private lateinit var posts: List<Post>
    private var posts = readPosts()
        set(value) {
            field = value
            sync()
        }

    
    //private var nextId = posts.first().id + 1
    private var nextId = (posts.maxByOrNull { it.id }?.id ?: 0L) + 1L


    private val data = MutableLiveData(posts)


    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }

        }
        data.value = posts
    }
//           if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
//        }


//


    override fun share(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    sharedByMe = !post.sharedByMe,
                    shares = if (post.sharedByMe) post.shares - 1 else post.shares + 1
                )
            } else {
                post
            }

        }
        data.value = posts
    }

//            if (it.id != id) it else it.copy(sharedByMe = !it.sharedByMe, shares = if (it.sharedByMe) it.shares - 1 else it.shares + 1)
//        }
//        data.value = post


    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    private fun readPosts(): List<Post> {
       val file : File = context.filesDir.resolve(FILE_NAME)
        return if (file.exists()){
            file.reader().buffered().use {
                gson.fromJson(it, postsType)

            }
        }else {
            emptyList()
        }

//        prefs.getString(POSTS_KEY, null)?.let {
//            gson.fromJson(it, postsType)
//        } ?: emptyList()
    }

    private fun sync(){
        val file : File = context.filesDir.resolve(FILE_NAME)
        file.writer().buffered().use {
            it.write(gson.toJson(posts))
        }

//        prefs.edit {
//            putString(POSTS_KEY, gson.toJson(posts))
//        }
    }


    private companion object {
        const val FILE_NAME = "posts.json"
        val postsType: Type = TypeToken.getParameterized(List::class.java, Post::class.java).type}

    override fun seve(post: Post) {
        posts = if (post.id == 0L) {
            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
        } else {
            posts.map {
                if (it.id == post.id) {
                    it.copy(content = post.content) // текст поста вы храните в поле content, соответственно обновлять нужно его, поле context можно удалить
                } else {
                    it
                }
            }
        }
        data.value = posts
    }


}


//    override fun like() {
//        post = post.copy(likedByMe = !post.likedByMe, likes = if (post.likedByMe) post.likes - 1 else post.likes + 1)
//        data.value = post
//    }

//    override fun share() {
//        post = if (post.sharedByMe) {
//            post.copy(shares = post.shares - 1, sharedByMe = false)
//        } else {
//            post.copy(shares = post.shares + 1, sharedByMe = true)
//        }
//        data.value = post
//    }




