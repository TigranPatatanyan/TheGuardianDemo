package com.tigran.data.network.service

import com.tigran.data.network.pojo.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("search")
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int,
        @Query("api-key") apiKey: String
    ): Response<ArticleResponse>

    @GET("search")
    suspend fun searchArticles(
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int,
        @Query("api-key") apiKey: String
    ): Response<ArticleResponse>
}