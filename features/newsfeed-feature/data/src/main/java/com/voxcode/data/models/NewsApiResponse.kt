package com.voxcode.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class NewsResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)

@JsonClass(generateAdapter = true)
internal data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val publishedAt: String?,
)

@JsonClass(generateAdapter = true)
internal data class SourceDto(
    val id: String?,
    val name: String,
)

