package com.azp.newsapikotlin.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azp.newsapikotlin.R
import com.azp.newsapikotlin.model.Article
import com.azp.newsapikotlin.toSimpleString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_article_list.view.*

class ArticleListAdapter(var articleList: List<Article> = ArrayList()):
    RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article_list,parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articleList.get(position))
    }

    fun updateList(article: List<Article>){
        this.articleList = article
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(itemView: View):
            RecyclerView.ViewHolder(itemView) {

        private var view: View = itemView
        private lateinit var article: Article

        fun bindArticle(article: Article) {
            this.article = article
            Picasso.get()
                .load(article.urlToImage)
                .placeholder(R.drawable.loading)
                .into(view.articleImage)
            view.articleTitle.text = article.title
            view.articleDescription.text = article.description
            view.articleDate.text = toSimpleString(article.publishedAt)
        }
    }
}