package com.voxcode.data.usecaes

import com.voxcode.domain.models.NewsPage
import com.voxcode.domain.repository.NewsRepository
import com.voxcode.domain.usecases.NewsUseCase
import javax.inject.Inject

internal class NewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : NewsUseCase {
    override suspend fun invoke(
        country: String,
        page: Int,
        pageSize: Int
    ): Result<NewsPage> {
        return newsRepository.getTopHeadlines(country, page, pageSize)
    }
}