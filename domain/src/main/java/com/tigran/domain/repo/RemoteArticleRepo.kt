package com.tigran.domain.repo

import com.tigran.domain.model.Article

interface RemoteArticleRepo {
    suspend fun searchArticles(query: String? = null, page: Int, pageSize: Int = 50): List<Article>?
}