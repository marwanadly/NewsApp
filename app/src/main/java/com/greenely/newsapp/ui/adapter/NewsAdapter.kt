package com.greenely.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.greenely.newsapp.R
import com.greenely.newsapp.data.model.Article
import com.greenely.newsapp.ui.interfaces.OnNewsClick
import kotlinx.android.synthetic.main.news_item.view.*

class NewsAdapter(private val onNewsClick: OnNewsClick) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.news_item,
                parent,
                false
            ),
            onNewsClick
        )

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(article = newsList[position])
    }

    fun addAll(list: List<Article>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }

    class NewsViewHolder(private val newsView: View, private val onNewsClick: OnNewsClick) : RecyclerView.ViewHolder(newsView) {

        fun bind(article: Article) {
            newsView.newsTitle.text = article.title
            newsView.newsDescription.text = article.description
            newsView.newsTime.text = article.getArticleTimeFormated()
            newsView.newsImage.load(article.imageUrl) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            newsView.newsContainer.setOnClickListener { onNewsClick.onNewsClick(article.newsUrl) }
        }
    }
}