package com.voxcode.domain.models

import kotlin.time.Instant

data class NewsPage(
    val articles: List<Article>,
    val totalResults: Int
)

data class Article(
    val source: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: Instant?
)