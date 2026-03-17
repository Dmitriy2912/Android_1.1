package ru.netology.nmedia.activity

import android.os.Bundle
import android.telephony.PhoneNumberUtils.formatNumber
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmobel.PostViewModel



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

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
                )
               

            }
        }
        binding.like.setOnClickListener {
            viewModel.like()

            fun formatNumber(number: Int): String {
                return when {
                    number < 0 -> "0"
                    number < 1_000 -> number.toString()
                    number < 10_000 -> {
                        val hundreds = number / 100
                        String.format("%.1f", hundreds / 10.0) + "K"
                    }

                    number < 1_000_000 -> "${number / 1_000}K"
                    else -> {
                        val millionsHundreds = number / 100_000
                        String.format("%.1f", millionsHundreds / 10.0) + "M"
                    }
                }
            }


        }
    }
}






