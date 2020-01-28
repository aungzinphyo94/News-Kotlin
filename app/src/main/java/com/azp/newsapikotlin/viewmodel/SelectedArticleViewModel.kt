package com.azp.newsapikotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azp.newsapikotlin.model.Article

class SelectedArticleViewModel : ViewModel() {

    private var selectedArticle: MutableLiveData<Article> = MutableLiveData()

    fun getSelectedArticle(): LiveData<Article> = selectedArticle

    fun selectedArticle(article: Article) {
        selectedArticle.value = article
    }
}