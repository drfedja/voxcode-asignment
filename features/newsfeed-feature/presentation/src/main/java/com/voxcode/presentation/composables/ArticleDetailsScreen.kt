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

        if (description.isNotBlank()) {
            Text(
                text = description,
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Button(
            onClick = onReadArticle,
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Text("Read full article")
        }
    }
}
