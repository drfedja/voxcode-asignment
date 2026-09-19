package com.voxcode.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.voxcode.core_ui.base.BaseViewModel
import com.voxcode.core_ui.screen_state.ScreenState
import com.voxcode.domain.models.Article
import com.voxcode.domain.usecases.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ArticlesViewModel @Inject constructor(
    private val useCase: NewsUseCase
) : BaseViewModel<ArticlesViewModel.ViewState>() {

    override fun getInitialState(): ViewState = ViewState()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = PAGE_SIZE
            ).onSuccess { result ->
                reduce(
                    articles = result.articles,
                    screenState = ScreenState.Success
                )
            }.onFailure {
                reduce(
                    screenState = ScreenState.Failure(
                        it.message ?: "Unknown error"
                    )
                )
            }
        }
    }

    private fun reduce(
        articles: List<Article>? = null,
        screenState: ScreenState? = null
    ) {
        reduceState {
            it.copy(
                articles = articles ?: it.articles,
                screenState = screenState ?: it.screenState
            )
        }
    }

    data class ViewState(
        val screenState: ScreenState = ScreenState.Loading,
        val articles: List<Article> = emptyList(),
        val isRefreshing: Boolean = false,
        val isLoadingNextPage: Boolean = false,
        val hasMore: Boolean = true,
        val paginationError: String? = null
    )

    companion object {
        private const val PAGE_SIZE = 10
    }
}