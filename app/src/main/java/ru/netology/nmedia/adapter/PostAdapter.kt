package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.formatNumber

typealias LikeListener = (Post) -> Unit
typealias ShareListener = (Post) -> Unit

class PostAdapter(
    private val shareListener: ShareListener
): ListAdapter<Post, PostViewHolder>( PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, likeListener, shareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)

        holder.bind(post)
    }


    }


class PostViewHolder(
    private val binding: CardPostBinding,
    private val likeListener: LikeListener,
    private val shareListener: ShareListener
) : RecyclerView.ViewHolder(binding.root){
fun bind(post: Post) {
    with(binding) {
        author.text = post.author
        published.text = post.published
        content.text = post.content
        like.setImageResource(
            if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_like_24
        )
        numberOfLikes.text = formatNumber(post.likes)
        numberOfReposts.text = formatNumber(post.shares)

        repost.setOnClickListener { shareListener(post) }
        like.setOnClickListener { likeListener(post) }
    }
}
}
object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id


    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}

