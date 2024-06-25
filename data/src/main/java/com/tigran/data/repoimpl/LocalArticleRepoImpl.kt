package com.tigran.data.repoimpl

import com.tigran.data.db.dao.ArticleDao
import com.tigran.domain.model.Article
import com.tigran.domain.repo.LocalArticleRepo

class LocalArticleRepositoryImpl(
    private val articleDao: ArticleDao
) : LocalArticleRepo {

    override suspend fun getArticleById(id: Int): Article? {
        return articleDao.getArticleById(id)
    }

    override suspend fun saveArticle(article: Article) {
        articleDao.insert(article)
    }

    override suspend fun getAllArticles(): List<Article> {
        return articleDao.getAllArticles()
    }

    override suspend fun releaseCache() {
        articleDao.deleteAllArticles()
    }
}
