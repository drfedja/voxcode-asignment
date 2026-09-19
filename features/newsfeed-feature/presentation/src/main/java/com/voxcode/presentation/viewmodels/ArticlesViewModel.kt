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

    override fun getInitialState() = ViewState(
        loadNextPage = ::loadNextPage,
        onRefresh = ::refresh
    )

    private var currentPage = 1

    init {
        loadPage(page = 1, isRefresh = false)
    }

    private fun loadPage(
        page: Int,
        isRefresh: Boolean
    ) {
        viewModelScope.launch {
            reduceState {
                it.copy(
                    screenState = ScreenState.Loading,
                    isRefreshing = isRefresh,
                    isLoadingNextPage = !isRefresh,
                    paginationError = null,
                    hasMore = true
                )
            }

            useCase.invoke(
                country = "us",
                page = page,
                pageSize = PAGE_SIZE
            ).onSuccess { result ->

                currentPage = page

                reduceState {
                    val articles = if (isRefresh) {
                        result.articles
                    } else {
                        it.articles + result.articles
                    }

                    it.copy(
                        articles = articles,
                        screenState = ScreenState.Success,
                        isRefreshing = false,
                        isLoadingNextPage = false,
                        hasMore = result.articles.isNotEmpty() &&
                                articles.size < result.totalResults,
                        paginationError = null
                    )
                }
            }.onFailure { throwable ->
                reduceState {
                    it.copy(
                        isRefreshing = false,
                        isLoadingNextPage = false,
                        screenState = if (isRefresh) {
                            ScreenState.Failure(
                                throwable.message ?: "Unable to load articles"
                            )
                        } else {
                            ScreenState.Success
                        },
                        paginationError = if (isRefresh) {
                            null
                        } else {
                            throwable.message ?: "Unable to load more articles"
                        }
                    )
                }
            }
        }
    }

    private fun refresh() {
        currentPage = 1
        loadPage(
            page = 1,
            isRefresh = true
        )
    }

    private fun loadNextPage() {
        val currentState = state.value

        if (
            currentState.isLoadingNextPage ||
            currentState.isRefreshing ||
            !currentState.hasMore
        ) {
            return
        }

        loadPage(
            page = currentPage + 1,
            isRefresh = false
        )
    }

    data class ViewState(
        val screenState: ScreenState = ScreenState.Loading,
        val articles: List<Article> = emptyList(),
        val isRefreshing: Boolean = false,
        val isLoadingNextPage: Boolean = false,
        val hasMore: Boolean = true,
        val paginationError: String? = null,
        val loadNextPage: () -> Unit,
        val onRefresh: () -> Unit
    )

    companion object {
        const val PAGE_SIZE = 5
    }
}
