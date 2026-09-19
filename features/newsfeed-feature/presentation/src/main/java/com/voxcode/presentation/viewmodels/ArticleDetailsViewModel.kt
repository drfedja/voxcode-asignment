package com.voxcode.presentation.viewmodels

import com.voxcode.core_ui.base.BaseViewModel
import com.voxcode.core_ui.screen_state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ArticleDetailsViewModel @Inject constructor() :
    BaseViewModel<ArticleDetailsViewModel.ViewState>() {

    override fun getInitialState(): ViewState {
        return ViewState(
            setArticle = { author, title, date, description, articleUrl ->
                setArticle(
                    author = author,
                    title = title,
                    date = date,
                    description = description,
                    articleUrl = articleUrl
                )
            }
        )
    }

    private fun setArticle(
        author: String,
        title: String,
        date: String,
        description: String,
        articleUrl: String
    ) {
        reduceState {
            it.copy(
                screenState = ScreenState.Success,
                author = author,
                title = title,
                date = date,
                description = description,
                articleUrl = articleUrl
            )
        }
    }

    data class ViewState(
        val screenState: ScreenState = ScreenState.Loading,
        val author: String = "",
        val title: String = "",
        val date: String = "",
        val description: String = "",
        val articleUrl: String = "",
        val setArticle: (
            author: String,
            title: String,
            date: String,
            description: String,
            articleUrl: String
        ) -> Unit = { _, _, _, _, _ -> }
    )
}