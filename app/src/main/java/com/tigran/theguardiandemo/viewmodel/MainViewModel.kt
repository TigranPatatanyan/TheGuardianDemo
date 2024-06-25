package com.tigran.theguardiandemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tigran.domain.model.Article
import com.tigran.domain.usecase.ArticleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val articleUseCase: ArticleUseCase) : ViewModel() {

    private val _articles = MutableSharedFlow<List<Article>?>(replay = 1)
    val articles = _articles.asSharedFlow()

    private val _error = MutableSharedFlow<Unit>()
    val error = _error.asSharedFlow()

    private var keyword = ""

    fun requestArticleData(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = articleUseCase.getArticles(keyword, page) {
                throwNetworkError()
            }
            withContext(Dispatchers.Main) {
                _articles.run {
                    emit(articles)
                }
            }
        }
    }

    fun cacheArticle(article: Article, onComplete: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCase.cacheArticle(article)
            onComplete.invoke()
        }
    }

    fun clearHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            articleUseCase.releaseCache()
        }
    }

    private fun throwNetworkError() {
        viewModelScope.launch(Dispatchers.Main) { _error.emit(Unit) }
    }

    fun updateSearchKeyword(keyword: String) {
        this.keyword = keyword
        viewModelScope.launch(Dispatchers.Main) {
            _articles.emit(null)
            requestArticleData(1)
        }
    }
}