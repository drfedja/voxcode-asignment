package com.voxcode.data.repository

import com.voxcode.api.ApiHandler
import com.voxcode.data.api.NewsApi
import com.voxcode.data.mappers.toDomain
import com.voxcode.domain.models.NewsPage
import com.voxcode.domain.repository.NewsRepository
import javax.inject.Inject

internal class NewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi
) : NewsRepository, ApiHandler {

    override suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int,
    ): Result<NewsPage> {
        return handleApi(
            execute = {
                newsApi.getTopHeadlines(
                    country = country,
                    pageSize = pageSize,
                    page = page
                )
            },
            mapper = {
                this.toDomain()
            }
        )
    }
}