package com.tigran.data.repoimpl

import com.tigran.data.network.pojo.ArticlePojo
import com.tigran.data.network.service.NewsService
import com.tigran.domain.model.Article
import com.tigran.domain.repo.RemoteArticleRepo

class RemoteArticleRepoImpl(private val networkService: NewsService) : RemoteArticleRepo {
    override suspend fun searchArticles(query: String?, page: Int, pageSize: Int): List<Article> {

        var responseList: List<ArticlePojo>?
        try {
            val response = query?.takeIf { it.isNotEmpty() }
                ?.let { networkService.searchArticles(query, page, 10, API_KEY) }
                ?: networkService.searchArticles(
                    page, pageSize,
                    API_KEY
                )
            responseList = if (response.isSuccessful)
                response.body()?.response?.results
            else emptyList()

        } catch (_: Exception) {
            responseList = emptyList()
        }
        return responseList?.mapToModel() ?: emptyList()
    }

    companion object {
        const val API_KEY = "test"
    }
}


fun List<ArticlePojo>.mapToModel(): List<Article> {
    val newList = mutableListOf<Article>()
    forEach {
        newList.add(
            Article(
                id = 0,
                date = it.webPublicationDate,
                title = it.webTitle,
                url = it.webUrl,
                apiUrl = it.apiUrl,
                bodyText = it.fields?.bodyText,
                thumbnail = it.fields?.thumbnail
            )
        )
    }
    return newList
}