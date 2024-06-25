package com.tigran.theguardiandemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tigran.domain.model.Article
import com.tigran.theguardiandemo.R

class ArticleAdapter(private val onItemClick: (Article) -> Unit) :
    ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvPublicationDate: TextView = itemView.findViewById(R.id.tvPublicationDate)
        private val ivThumbnail: ImageView = itemView.findViewById(R.id.ivThumbnail)

        fun bind(article: Article, onItemClick: (Article) -> Unit) {
            tvTitle.text = article.title
            tvPublicationDate.text = article.date

            Glide.with(itemView)
                .load(article.thumbnail+"?api-key=test")
                .into(ivThumbnail)

            itemView.setOnClickListener { onItemClick(article) }
        }
    }

    class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    // Optionally, you can add this method to add data
    fun addData(newData: List<Article>) {
        submitList(currentList.toMutableList().plus(newData))
    }
}