package com.voxcode.data.di

import com.voxcode.data.api.NewsApi
import com.voxcode.provider.BaseUrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NewsApiModule {

    @Provides
    @Singleton
    @Named(NEWS_API_CLIENT)
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun providesNewsApi(
        baseUrlProvider: BaseUrlProvider,
        @Named(NEWS_API_CLIENT) okHttpClient: OkHttpClient
    ): NewsApi {
        return Retrofit.Builder()
            .baseUrl(baseUrlProvider.provideNewsBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    companion object {
        private const val NEWS_API_CLIENT = "news_api_client"
    }
}
