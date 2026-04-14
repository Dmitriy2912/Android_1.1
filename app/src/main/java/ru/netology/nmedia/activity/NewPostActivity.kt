package ru.netology.nmedia.activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import kotlin.jvm.java


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isBlank()){
                setResult(Activity.RESULT_CANCELED)
            }else {
                val intent = Intent().putExtra(NewPostContract.KEY_TEXT, text)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
        binding.ok.setOnClickListener {
            val newText = binding.edit.text.toString()
            if (newText.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                val resultIntent = Intent().putExtra(EditPostContract.KEY_UPDATED_TEXT, newText)
                setResult(RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}
object EditPostContract : ActivityResultContract<String, String?>() {
    const val KEY_EDIT_TEXT = "edit_text"
    const val KEY_UPDATED_TEXT = "updated_text"
    override fun createIntent(context: Context, input: String): Intent =
        Intent(context, NewPostActivity::class.java).putExtra(KEY_EDIT_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK && intent != null) {

            binding.edit.setText(intent?.getStringExtra(EditPostContract.KEY_EDIT_TEXT))
        } else null

    }



object NewPostContract: ActivityResultContract<Unit, String?>() {
    const val KEY_TEXT = "post_text"

    override fun createIntent(context: Context, input: Unit) =
        Intent(context, NewPostActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?) = intent?.getStringExtra(KEY_TEXT)
}
