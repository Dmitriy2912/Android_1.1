package ru.netology.nmedia.repository

import android.content.Context

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.netology.nmedia.dto.Post
import java.io.File
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class JsonFilePostRepositoryImpl(private val context: Context) : PostRepository {

    //private val prefs = context.getSharedPreferences("posts", MODE_PRIVATE)
   // private val gson = Gson()
    //private val postsFile = File(context.filesDir, "posts.json")
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val file: File = context.filesDir.resolve(FILE_NAME)
    private var posts1: MutableList<Post> = mutableListOf()
    private val data1 = MutableLiveData<List<Post>>()






    //private lateinit var posts: List<Post>
    private var posts = readPosts()
        set(value) {
            field = value
            sync()
        }

//    private val postsType: Type = TypeToken.getParameterized(
//        List::class.java,
//        Post::class.java
//    ).type
//
//    private var postss = readPosts()
//        set(value) {
//            field = value
//            sync()
//        }



    
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

    override fun seve(post: Post) {
        posts = if (post.id == 0L) {
            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
        } else {
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        data.value = posts
    }

    override fun loadPosts() {
        val loadedPosts = readPosts()
        posts = loadedPosts
        data.value = loadedPosts
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
        val postsType: Type = TypeToken.getParameterized(List::class.java, Post::class.java).type
        private const val FILE_NAME = "posts_data.json"
    }





//    override fun save(post: Post) {
//        if (post.id == 0L) {
//            val newId = generateNextId()
//            val newPost = post.copy(
//                id = newId,
//                author = "Me",
//                published = getCurrentTimestamp()
//            )
//            posts.add(0, newPost)
//        } else {
//            val index = posts.indexOfFirst { it.id == post.id }
//            if (index != -1) {
//                posts[index] = posts[index].copy(content = post.content)
//            }
//        }
//        saveToFile()
//        data.value = posts.toList()
//    }



    private fun saveToFile() {
        try {
            file.parentFile?.mkdirs()
            file.bufferedWriter().use { writer ->
                gson.toJson(posts, writer)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun generateNextId(): Long {
        return if (posts.isEmpty()) 1L else (posts.maxOf { it.id } + 1)
    }

    private fun getCurrentTimestamp(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
    }


}






//    override fun seve(post: Post) {
//        posts = if (post.id == 0L) {
//            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
//        } else {
//            posts.map {
//                if (it.id == post.id) {
//                    it.copy(content = post.content) // текст поста вы храните в поле content, соответственно обновлять нужно его, поле context можно удалить
//                } else {
//                    it
//                }
//            }
//        }
//        data.value = posts
//    }








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




