package ru.netology.nmedia.repository

import android.R.id
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post


class PostRepositoryInMemoryImpl : PostRepository {


    private var post = listOf(
        Post(
            id = 2,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "18 сентября в 10:12",
            likes = 20,
            likedByMe = false,
            likeById = 20,
            shares = 20,
            sharedByMe = 200

        ),
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 10,
            likedByMe = false,
            likeById = 20,
            shares = 20,
            sharedByMe = 200
        )

    )


    private val data = MutableLiveData(post)


    override fun get(): LiveData<List<Post>> = data

    override fun like(id: Long) {
        val posts = post.indexOfFirst { it.id == id }
        if (posts != -1){
            val currentPost = post[posts]
            post[posts] = if (currentPost.likedByMe) {
                currentPost.copy(likes = currentPost.likes - 1, likedByMe = false)
            } else {
               currentPost.copy(likes = currentPost.likes + 1, likedByMe = true)
            }
            data.value = post
        }
    }

    override fun share(id: Long) {
        val posts = post.indexOfFirst { it.id == id }
        if (posts != -1){
            val currentPost = post[posts]
             post[posts] = if (currentPost.sharedByMe) {
                currentPost.copy(shares = currentPost.shares - 1, sharedByMe = false)
            } else {
                currentPost.copy(shares = currentPost.shares +1, sharedByMe = true)
            }
        }
    }

    override fun likeById(id: Long) {
        post = post.map {
                if (it.id != id) it else it.copy(likedByMe = !it.likedByMe, likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
            }
        data.value = post
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




