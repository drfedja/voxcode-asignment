package com.voxcode.domain.repository

import com.voxcode.domain.models.NewsPage

interface NewsRepository {
    suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int,
    ): Result<NewsPage>
}