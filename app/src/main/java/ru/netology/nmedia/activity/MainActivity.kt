package ru.netology.nmedia.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.AndroidUtils
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
        val adapter = PostAdapter(
            { post -> viewModel.share(post.id) },
            { post -> viewModel.likeById(post.id) },
            { post -> viewModel.removeById(post.id) },
            {post -> viewModel.edit(post)} as () -> Unit

        )

        binding.List.adapter = adapter
        viewModel.data.observe(this) { post ->
            adapter.submitList(post)

        }
        binding.seve.setOnClickListener {
            val content = binding.content.text?.toString().orEmpty()

            if (content.isBlank()) {
                Toast.makeText(
                    this,
                    R.string.content_is_blank_error,
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            viewModel.seveContent(content)
            binding.content.setText("")
            binding.content.clearFocus()

            AndroidUtils.hideKeyboard(binding.content)
        }
        viewModel.edited.observe(this) { edited ->
            if (edited.id != 0L) {
                with(binding.content) {
                    AndroidUtils.showKeyboard(this)
                    setText("")
                    append(edited.content)
                }
            }
        }
        viewModel.edited.observe(this){ edited ->
            if (edited.id != 0L) {
                with(binding.content) {
                    AndroidUtils.showKeyboard(this)
                    setText("")
                    append(edited.content)
            }
        }else{
                binding.content.visibility = View.GONE

        }

        }
        binding.cancel.setOnClickListener {
            viewModel.clearEdit()
            binding.content.setText("")


        }


        //binding.root.setOnClickListener { viewModel.like() }
        //binding.root.setOnClickListener { viewModel.share() }


    }
}










