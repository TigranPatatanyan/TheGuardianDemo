package com.tigran.domain.usecase

import com.tigran.domain.model.Article
import com.tigran.domain.repo.LocalArticleRepo
import com.tigran.domain.repo.RemoteArticleRepo

class ArticleUseCase(
    private val remoteArticleRepo: RemoteArticleRepo,
    private val localArticleRepo: LocalArticleRepo
) {
    suspend fun getArticles(keyword: String, page: Int, function: () -> Unit): List<Article> {
        val articles: List<Article>? = remoteArticleRepo.searchArticles(keyword, page)
        if (articles.isNullOrEmpty()) {
            function.invoke()
            return localArticleRepo.getAllArticles()
        } else return articles
    }

    suspend fun cacheArticle(article: Article) {
        localArticleRepo.saveArticle(article)
    }

    suspend fun releaseCache() {
        localArticleRepo.releaseCache()
    }
}