package com.voxcode.data.di

import com.voxcode.data.usecaes.NewsUseCaseImpl
import com.voxcode.domain.usecases.NewsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
internal interface NewsUseCaseModule {

    @Binds
    @ActivityRetainedScoped
    fun bindNewsUseCase(
        newsUseCaseImpl: NewsUseCaseImpl
    ): NewsUseCase
}
