package com.tigran.data.network.pojo

data class ArticleResponse(
    val response: Response
)

data class Response(
    val status: String,
    val userTier: String,
    val total: Int,
    val startIndex: Int,
    val pageSize: Int,
    val currentPage: Int,
    val pages: Int,
    val orderBy: String,
    val results: List<ArticlePojo>
)

data class ArticlePojo(
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String,
    val fields: ArticleFields?
)

data class ArticleFields(
    val thumbnail: String?,
    val bodyText: String?
)