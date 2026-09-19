package com.voxcode.data.models

internal data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)

internal data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: String?,
)

internal data class SourceDto(
    val id: String?,
    val name: String,
)

