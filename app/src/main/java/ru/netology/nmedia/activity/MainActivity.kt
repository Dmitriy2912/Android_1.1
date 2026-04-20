package ru.netology.nmedia.activity


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmobel.PostViewModel




class MainActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
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
        val newPostLauncher = registerForActivityResult(EditPostContract.NewPostContract) {
            val result = it ?: return@registerForActivityResult
            viewModel.seveContent(result)
        }
        val adapter = PostAdapter(
            { post -> viewModel.share(post.id) },
            { post -> viewModel.likeById(post.id) },
            { post -> viewModel.removeById(post.id) },
            { post -> viewModel.edit(post) },

        )
        val editPostLauncher = registerForActivityResult(EditPostContract) {
            val result = it ?: return@registerForActivityResult
            viewModel.seveContent(result)
        }





        binding.List.adapter = adapter
        viewModel.data.observe(this) { post ->
            adapter.submitList(post)

        }

//        override fun formatNumber(post: Post) {
//            val intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                type = "text/plain"
//                putExtra(Intent.EXTRA_TEXT, post.content)
//            }
//            val chooser =
//                Intent.createChooser(intent, getString(R.string.desription_post_author_avtor))
//            startActivity(chooser)
//        }

        binding.add.setOnClickListener {
            newPostLauncher.launch()

        }

        }



    }











