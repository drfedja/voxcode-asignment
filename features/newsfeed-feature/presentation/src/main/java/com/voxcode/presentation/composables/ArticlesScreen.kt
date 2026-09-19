package com.voxcode.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.voxcode.core_ui.composable.Screen
import com.voxcode.core_ui.screen_state.ScreenState
import com.voxcode.domain.models.Article
import com.voxcode.presentation.viewmodels.ArticlesViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.toJavaInstant

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

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ArticlesContent(
                articles = viewState.articles,
                onNavigate = onNavigate
            )

            if (viewState.screenState is ScreenState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            if (viewState.screenState is ScreenState.Failure) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = viewState.screenState.message
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ArticlesContent(
    articles: List<Article>,
    onNavigate: (
        author: String,
        title: String,
        date: String,
        description: String,
        article: String
    ) -> Unit) {
    LazyColumn {
        items(
            items = articles,
            key = { it.url }
        ) { article ->

            ArticleListItem(
                article = article,
                onClick = {
                    onNavigate(
                        article.author ?: "",
                        article.title,
                        article.publishedAt?.toString() ?: "",
                        article.description ?: "",
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
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            )
    ) {
        Text(
            text = article.title,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = article.source,
            modifier = Modifier.padding(top = 4.dp)
        )

        article.publishedAt?.let { instant ->
            Text(
                text = formatArticleDate(instant),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    HorizontalDivider()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun formatArticleDate(
    instant: kotlin.time.Instant
): String {
    return DateTimeFormatter.ofPattern("MMM d, yyyy")
        .withZone(ZoneId.systemDefault())
        .format(instant.toJavaInstant())
}


