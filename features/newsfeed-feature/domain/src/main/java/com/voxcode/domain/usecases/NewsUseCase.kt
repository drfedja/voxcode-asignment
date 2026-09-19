package com.voxcode.domain.usecases

import com.voxcode.domain.models.NewsPage

interface NewsUseCase {
    suspend fun invoke(
        country: String,
        page: Int,
        pageSize: Int,
    ): Result<NewsPage>
}