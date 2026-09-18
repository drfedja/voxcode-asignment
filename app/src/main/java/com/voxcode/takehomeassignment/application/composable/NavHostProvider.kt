package com.voxcode.takehomeassignment.application.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.voxcode.core_ui.composable.LocalNavController
import com.voxcode.takehomeassignment.application.navigation.NavGraph

@Composable
fun NavHostProvider() {
    val navController = rememberNavController()

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
//                ArticlesListScreen()
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

//                ArticleDetailsScreen(
//                    title = title,
//                    article = article,
//                    author = author,
//                    date = date,
//                    description = description
//                )
            }
        }
    }
}