package com.voxcode.data.mappers

import com.voxcode.data.models.ArticleDto
import com.voxcode.data.models.NewsResponseDto
import com.voxcode.domain.models.Article
import com.voxcode.domain.models.NewsPage
import kotlin.time.Instant

internal fun NewsResponseDto.toDomain(): NewsPage {
    return NewsPage(
        articles = articles.map { it.toDomain() },
        totalResults = totalResults
    )
}

internal fun ArticleDto.toDomain(): Article {
    return Article(
        source = source.name,
        author = author,
        title = title,
        description = description,
        url = url,
        publishedAt = publishedAt?.let { Instant.parse(it) }
    )
}
