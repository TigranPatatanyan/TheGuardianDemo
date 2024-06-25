package com.tigran.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: String,
    val title: String,
    val url: String,
    val apiUrl: String,
    val thumbnail: String? = null,
    val bodyText: String? = null
)