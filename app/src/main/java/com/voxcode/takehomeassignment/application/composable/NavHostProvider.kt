package com.voxcode.takehomeassignment.application.composable

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.voxcode.core_ui.composable.LocalNavController
import com.voxcode.presentation.composables.ArticleDetailsScreen
import com.voxcode.presentation.composables.ArticlesScreen
import com.voxcode.takehomeassignment.application.navigation.NavGraph
import androidx.core.net.toUri

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostProvider() {
    val navController = rememberNavController()
    val context = LocalContext.current

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            navController = navController,
            startDestination = NavGraph.ArticlesList.route
        ) {
            composable(
                route = NavGraph.ArticlesList.route
            ) {
                ArticlesScreen(
                    onNavigate = { title, article, author, date, description ->
                        navController.navigate(
                            NavGraph.ArticleDetails.createRoute(
                                title = title,
                                article = article,
                                author = author,
                                date = date,
                                desc = description
                            )
                        )
                    }
                )
            }

            composable(
                route = NavGraph.ArticleDetails.route
            ) { backstackEntry ->
                val title = backstackEntry
                    .arguments?.getString(NavGraph.ArticleDetails.TITLE).orEmpty()
                val article = backstackEntry
                    .arguments?.getString(NavGraph.ArticleDetails.ARTICLE).orEmpty()
                val author = backstackEntry
                    .arguments?.getString(NavGraph.ArticleDetails.AUTHOR).orEmpty()
                val date = backstackEntry
                    .arguments?.getString(NavGraph.ArticleDetails.DATE).orEmpty()
                val description = backstackEntry
                    .arguments?.getString(NavGraph.ArticleDetails.DESC).orEmpty()

                ArticleDetailsScreen(
                    author = author,
                    title = title,
                    date = date,
                    description = description,
                    articleUrl = article,
                    onReadArticle = {
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                description.toUri()
                            )
                        )
                    }
                )
            }
        }
    }
}