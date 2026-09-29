package com.voxcode.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.voxcode.core_ui.composable.Screen
import com.voxcode.core_ui.screen_state.ScreenState
import com.voxcode.domain.models.Article
import com.voxcode.presentation.util.formatArticleDate
import com.voxcode.presentation.viewmodels.ArticlesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticlesScreen(
    onNavigate: (
        author: String,
        title: String,
        date: String,
        description: String,
        article: String
    ) -> Unit
) {
    Screen(
        viewModel = hiltViewModel<ArticlesViewModel>()
    ) { viewState ->

        PullToRefreshBox(
            isRefreshing = viewState.isRefreshing,
            onRefresh = viewState.onRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            when (val state = viewState.screenState) {

                is ScreenState.Loading -> {
                    if (viewState.articles.isNotEmpty()) {
                        ArticlesContent(
                            articles = viewState.articles,
                            hasMore = viewState.hasMore,
                            isLoadingNextPage = viewState.isLoadingNextPage,
                            onLoadNextPage = viewState.loadNextPage,
                            onNavigate = onNavigate
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ScreenState.Success -> {
                    ArticlesContent(
                        articles = viewState.articles,
                        hasMore = viewState.hasMore,
                        isLoadingNextPage = viewState.isLoadingNextPage,
                        onLoadNextPage = viewState.loadNextPage,
                        onNavigate = onNavigate
                    )
                }

                is ScreenState.Failure -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Box(
                                modifier = Modifier.fillParentMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = state.message)
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ArticlesContent(
    articles: List<Article>,
    hasMore: Boolean,
    isLoadingNextPage: Boolean,
    onLoadNextPage: () -> Unit,
    onNavigate: (
        author: String,
        title: String,
        date: String,
        description: String,
        article: String
    ) -> Unit
) {
    val listState = rememberLazyListState()

    val loadNextPage by rememberUpdatedState(onLoadNextPage)
    val canLoadNextPage by rememberUpdatedState(
        hasMore && !isLoadingNextPage
    )

    fun tryLoadNextPage() {
        if (!canLoadNextPage) return

        val layoutInfo = listState.layoutInfo
        val lastVisibleItem =
            layoutInfo.visibleItemsInfo.lastOrNull()?.index

        val isAtEnd =
            lastVisibleItem != null &&
                    lastVisibleItem == layoutInfo.totalItemsCount - 1

        if (isAtEnd) {
            loadNextPage()
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {

            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (
                    source == NestedScrollSource.UserInput &&
                    available.y < 0
                ) {
                    tryLoadNextPage()
                }

                return Offset.Zero
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                if (available.y < 0f) {
                    tryLoadNextPage()
                }

                return Velocity.Zero
            }
        }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(
            items = articles,
            key = Article::url
        ) { article ->
            ArticleListItem(
                article = article,
                onClick = {
                    onNavigate(
                        article.author.orEmpty(),
                        article.title,
                        article.publishedAt?.toString().orEmpty(),
                        article.description.orEmpty(),
                        article.url
                    )
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ArticleListItem(
    article: Article,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = article.title,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = "Source: ",
                fontSize = 12.sp
            )

            Text(
                text = article.source
            )
        }

        article.publishedAt?.let { instant ->
            Text(
                text = formatArticleDate(instant),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    HorizontalDivider()
}



