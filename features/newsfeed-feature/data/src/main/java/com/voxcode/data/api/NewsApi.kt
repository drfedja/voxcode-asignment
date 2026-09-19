package com.voxcode.data.api

import com.voxcode.data.models.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String = "us",
        @Query("pageSize") pageSize: Int = 10,
        @Query("page") page: Int
    ): Response<NewsResponseDto>
}
