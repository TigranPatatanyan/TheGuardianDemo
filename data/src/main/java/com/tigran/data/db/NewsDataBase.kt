package com.tigran.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tigran.data.db.dao.ArticleDao
import com.tigran.domain.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}