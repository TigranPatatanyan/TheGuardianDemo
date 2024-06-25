package com.tigran.theguardiandemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigran.domain.model.Article
import com.tigran.domain.repo.LocalArticleRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ArticleDetailedViewModel(private val localArticleRepo: LocalArticleRepo) : ViewModel() {

    private val _article = MutableSharedFlow<Article>()
    val article = _article.asSharedFlow()

    fun requestData(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            localArticleRepo.getArticleById(id)?.let { _article.emit(it) }
        }
    }
}