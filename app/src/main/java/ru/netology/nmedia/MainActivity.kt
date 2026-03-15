package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 10,
            likedByMe = false,
            shares = 10  // добавляем поле shares
        )
        fun formatNumber(number: Int): String {
            return when {
                number < 1_000 -> number.toString()
                number < 10_000 -> String.format("%.1f", number / 100.0) + "K"
                number < 1_000_000 -> "${number / 1_000}K"
                else -> String.format("%.1f", number / 1_000_000.0) + "M"
            }
        }


        fun updateLikesDisplay() {
            binding.like?.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
            )
            binding.numberOfLikes?.text = formatNumber(post.likes)
        }
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content


            // Инициализация отображения лайков
            updateLikesDisplay()
            updateLikesDisplay()

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            like?.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.likes++ else post.likes--
                updateLikesDisplay()
            }

            repost.setOnClickListener {
                Log.d("stuff", "share")
                post.shares++
                updateLikesDisplay()
            }

        }
    }
}


