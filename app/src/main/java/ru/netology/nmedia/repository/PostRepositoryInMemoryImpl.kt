package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl: PostRepository {


    private var post = listOf(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            published = "21 мая в 18:36",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            likes = 10,
            shares = 10,
            sharedByMe = TODO(),
            likeById = TODO(),
            like = TODO()
        ),
         Post(
             id = 2,
             author = "Нетология. Университет интернет-профессий будущего",
             published = "21 мая в 18:36",
             content = "Привет",
             likes = 10,
             shares = 10,
             sharedByMe = TODO(),
             likeById = TODO(),
             like = TODO()
         )
    )


    private val data = MutableLiveData(post)


    override fun get(): LiveData<List<Post>> = data

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

    override fun likeById(id: Int) {
        post = post.map {
            if (it.id.toLong() != id) it else it.copy( likeById = !it.likeById, like = if (it.likeById))
        }
        data.value = post
    }


}