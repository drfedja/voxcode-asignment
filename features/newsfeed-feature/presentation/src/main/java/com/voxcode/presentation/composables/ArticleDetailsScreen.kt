package com.voxcode.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.voxcode.core_ui.composable.Screen
import com.voxcode.core_ui.screen_state.ScreenState
import com.voxcode.presentation.viewmodels.ArticleDetailsViewModel

@Composable
fun ArticleDetailsScreen(
    author: String,
    title: String,
    date: String,
    description: String,
    articleUrl: String,
    onReadArticle: (String) -> Unit
) {
    Screen(
        viewModel = hiltViewModel<ArticleDetailsViewModel>()
    ) { viewState ->

        LaunchedEffect(Unit) {
            viewState.setArticle(
                author,
                title,
                date,
                description,
                articleUrl
            )
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ArticleDetailsContent(
                author = viewState.author,
                title = viewState.title,
                date = viewState.date,
                description = viewState.description,
                onReadArticle = {
                    onReadArticle(viewState.articleUrl)
                }
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

@Composable
private fun ArticleDetailsContent(
    author: String,
    title: String,
    date: String,
    description: String,
    onReadArticle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        ArticleHeader(
            title = title,
            author = author,
            date = date
        )

        if (description.isNotBlank()) {
            ArticleDescription(description = description)
        }

        ReadArticleButton(onClick = onReadArticle)
    }
}

@Composable
private fun ArticleHeader(
    title: String,
    author: String,
    date: String
) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )

    if (author.isNotBlank()) {
        Text(
            text = author,
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }

    if (date.isNotBlank()) {
        Text(
            text = date,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ArticleDescription(description: String) {
    Text(
        text = description,
        modifier = Modifier.padding(top = 16.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun ReadArticleButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        Text("Read full article")
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleHeaderPreview() {
    MaterialTheme {
        ArticleHeader(
            title = "Sample Article Title",
            author = "John Doe",
            date = "October 1, 2026"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleDescriptionPreview() {
    MaterialTheme {
        ArticleDescription(description = "This is a sample description for the article.")
    }
}

@Preview(showBackground = true)
@Composable
private fun ReadArticleButtonPreview() {
    MaterialTheme {
        ReadArticleButton(onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ArticleDetailsContentPreview() {
    MaterialTheme {
        ArticleDetailsContent(
            author = "John Doe",
            title = "Sample Article Title",
            date = "October 1, 2026",
            description = "This is a sample description for the article.",
            onReadArticle = {}
        )
    }
}

