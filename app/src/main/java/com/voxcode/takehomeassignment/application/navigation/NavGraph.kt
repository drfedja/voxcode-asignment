package com.voxcode.takehomeassignment.application.navigation

interface NavGraph {
    val route: String

    data object ArticlesList: NavGraph {
        override val route = "/"
    }

    data object ArticleDetails : NavGraph {
        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DATE = "date"
        const val DESC = "desc"
        const val ARTICLE = "article"

        override val route = "/articleDetails/{$AUTHOR}/{$TITLE}/{$DATE}/{$DESC}/{$ARTICLE}"

        fun createRoute(
            author: String,
            title: String,
            date: String,
            desc: String,
            article: String
        ) =
            "/articleDetails/$author/$title/$date/$desc/$article"
    }
}
