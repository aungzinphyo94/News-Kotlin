package com.azp.newsapikotlin.ui


import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.azp.newsapikotlin.R
import com.azp.newsapikotlin.model.Article
import com.azp.newsapikotlin.model.ArticleResult
import com.azp.newsapikotlin.ui.adapter.ArticleListAdapter
import com.azp.newsapikotlin.viewmodel.ArticleViewModel
import com.azp.newsapikotlin.viewmodel.SelectedArticleViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*

/**
 * A simple [Fragment] subclass.
 */
class ArticleListFragment : Fragment(),
    ArticleListAdapter.ClickListener {

    private lateinit var articleListAdapter: ArticleListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var articleViewModel: ArticleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewManager = LinearLayoutManager(activity)
        articleListAdapter = ArticleListAdapter()
        recyclerView.adapter = articleListAdapter
        recyclerView.layoutManager = viewManager
        articleListAdapter.setOnClickListener(this)
        observeViewModel()
    }

    fun observeViewModel() {
        articleViewModel = ViewModelProviders
            .of(this)
            .get(ArticleViewModel::class.java)
        articleViewModel.getResults().observe(
            this, Observer<ArticleResult> { result ->

                recyclerView.visibility = View.VISIBLE
                articleListAdapter.updateList(result.articles)

            }
        )

        articleViewModel.getError().observe(
            this, Observer<Boolean> { isError ->

                if (isError) {
                    txtError.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    txtError.visibility = View.GONE
                }

            }
        )

        articleViewModel.getLoading().observe(
            this, Observer<Boolean> { isLoading ->
                loadingView.visibility = (if (isLoading)
                    View.VISIBLE else View.INVISIBLE)
                if (isLoading) {
                    txtError.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        articleViewModel.loadResults()
    }

    override fun onClick(article: Article) {
        if (!TextUtils.isEmpty(article.url)){
            val selectedArticleViewModel: SelectedArticleViewModel =
                ViewModelProviders.of(activity!!).get(SelectedArticleViewModel::class.java)
            selectedArticleViewModel.selectedArticle(article)
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.screen_container,DetailsFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
