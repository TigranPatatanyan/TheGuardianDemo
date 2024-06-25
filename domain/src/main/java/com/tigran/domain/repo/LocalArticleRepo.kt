package com.tigran.domain.repo

import com.tigran.domain.model.Article

interface LocalArticleRepo {
    suspend fun getArticleById(id: Int): Article?
    suspend fun saveArticle(article: Article)
    suspend fun getAllArticles(): List<Article>
    suspend fun releaseCache()
}