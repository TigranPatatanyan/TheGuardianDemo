package com.tigran.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tigran.domain.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): List<Article>

    @Query("SELECT * FROM articles WHERE id = :id")
    suspend fun getArticleById(id: Int): Article?

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}
