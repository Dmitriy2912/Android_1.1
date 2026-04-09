package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.formatNumber



typealias LikeListener = (Post) -> Unit
typealias ShareListener = (Post) -> Unit
typealias RemoveListener = (Post) -> Unit
typealias EditListener = (Post) -> Unit

class PostAdapter(
    private val shareListener: ShareListener,
    private val likeListener: LikeListener,
    private val removeListener: RemoveListener,
    private val editListener: EditListener,
): ListAdapter<Post, PostViewHolder>( PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeListener , shareListener, removeListener, editListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)

        holder.bind(post)
    }


    }


class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeListener: LikeListener,
    private val shareListener: ShareListener,
    private val removeListener: RemoveListener,
    private val editListener: EditListener,
) : RecyclerView.ViewHolder(binding.root){

fun bind(post: Post) {

    with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content

        like.isChecked = post.likedByMe
        like.text = post.likes.toString()

        numberOfReposts.text = formatNumber(post.likes)
        numberOfReposts.text = formatNumber(post.shares)



        menu.setOnClickListener{ PopupMenu (it.context, it).apply {
            inflate(R.menu.menu_post)



            setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    R.id.remove -> {
                        removeListener(post)
                        true
                    }
                    R.id.edit -> {
                        editListener(post)
                        true
                    }
                    else -> false
                }
            }
             show()
        }

        }
        repost.setOnClickListener { shareListener(post) }
        like.setOnClickListener { likeListener(post) }

}
}


}
object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}



