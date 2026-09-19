package com.voxcode.presentation

import com.voxcode.core_ui.screen_state.ScreenState
import com.voxcode.domain.models.Article
import com.voxcode.domain.models.NewsPage
import com.voxcode.domain.usecases.NewsUseCase
import com.voxcode.presentation.viewmodels.ArticlesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class ArticlesViewModelUnitTests {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var useCase: NewsUseCase

    @Before
    fun setup() {
        useCase = mockk()
    }

    @Test
    fun `load next page appends articles`() = runTest {
        // given
        coEvery {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returns Result.success(firstPage)

        coEvery {
            useCase.invoke(
                country = "us",
                page = 2,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returns Result.success(secondPage)

        val viewModel = ArticlesViewModel(useCase)

        advanceUntilIdle()

        // when
        viewModel.state.value.loadNextPage()
        advanceUntilIdle()

        // then
        assertEquals(
            firstPage.articles + secondPage.articles,
            viewModel.state.value.articles
        )
    }

    @Test
    fun `refresh replaces articles and resets pagination`() = runTest {
        // given
        coEvery {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returnsMany listOf(
            Result.success(firstPage),
            Result.success(refreshedPage)
        )

        val viewModel = ArticlesViewModel(useCase)

        advanceUntilIdle()

        // when
        viewModel.state.value.onRefresh()
        advanceUntilIdle()

        // then
        assertEquals(
            refreshedPage.articles,
            viewModel.state.value.articles
        )

        assertEquals(
            false,
            viewModel.state.value.isRefreshing
        )

        assertEquals(
            true,
            viewModel.state.value.hasMore
        )

        assertEquals(
            null,
            viewModel.state.value.paginationError
        )
    }

    @Test
    fun `refresh failure sets failure state`() = runTest {
        // given
        val errorMessage = "Unable to refresh"

        coEvery {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returnsMany listOf(
            Result.success(firstPage),
            Result.failure(RuntimeException(errorMessage))
        )

        val viewModel = ArticlesViewModel(useCase)

        advanceUntilIdle()

        // when
        viewModel.state.value.onRefresh()
        advanceUntilIdle()

        // then
        assertEquals(
            false,
            viewModel.state.value.isRefreshing
        )

        assertEquals(
            firstPage.articles,
            viewModel.state.value.articles
        )

        assertEquals(
            null,
            viewModel.state.value.paginationError
        )

        assertEquals(
            ScreenState.Failure(errorMessage),
            viewModel.state.value.screenState
        )
    }

    @Test
    fun `refresh resets current page and next page starts from page two`() = runTest {
        // given
        coEvery {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returnsMany listOf(
            Result.success(firstPage),
            Result.success(refreshedPage)
        )

        coEvery {
            useCase.invoke(
                country = "us",
                page = 2,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        } returns Result.success(secondPage)

        val viewModel = ArticlesViewModel(useCase)

        advanceUntilIdle()

        // when
        viewModel.state.value.onRefresh()
        advanceUntilIdle()

        viewModel.state.value.loadNextPage()
        advanceUntilIdle()

        // then
        assertEquals(
            refreshedPage.articles + secondPage.articles,
            viewModel.state.value.articles
        )

        coVerify(exactly = 2) {
            useCase.invoke(
                country = "us",
                page = 1,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        }

        coVerify(exactly = 1) {
            useCase.invoke(
                country = "us",
                page = 2,
                pageSize = ArticlesViewModel.PAGE_SIZE
            )
        }
    }

    private val refreshedPage = NewsPage(
        articles = listOf(
            Article(
                source = "The Guardian",
                author = "Author 4",
                title = "Refreshed article",
                description = "Refreshed description",
                url = "https://example.com/4",
                publishedAt = null
            )
        ),
        totalResults = 10
    )

    private val article1 = Article(
        source = "BBC",
        author = "Author 1",
        title = "Article 1",
        description = "Description 1",
        url = "https://example.com/1",
        publishedAt = null
    )

    private val article2 = Article(
        source = "CNN",
        author = "Author 2",
        title = "Article 2",
        description = "Description 2",
        url = "https://example.com/2",
        publishedAt = null
    )

    private val article3 = Article(
        source = "Reuters",
        author = null,
        title = "Article 3",
        description = null,
        url = "https://example.com/3",
        publishedAt = null
    )

    private val firstPage = NewsPage(
        articles = listOf(article1, article2),
        totalResults = 4
    )

    private val secondPage = NewsPage(
        articles = listOf(article3),
        totalResults = 4
    )
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}