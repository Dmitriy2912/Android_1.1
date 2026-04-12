package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
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
        Intent(context, EditPostActivity::class.java).putExtra(KEY_EDIT_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK && intent != null) {
            intent.getStringExtra(KEY_UPDATED_TEXT)
        } else null


}