package com.chicken.chickenroad.presentation.navigation

sealed class NavRoutes(val route: String) {
    data object LoadScreenRoute : NavRoutes(route = "Load route")
    data object MenuScreenRoute : NavRoutes(route = "Menu route")
    data object LevelsScreenRoute : NavRoutes(route = "Levels route")
    data object SettingsScreenRoute : NavRoutes(route = "Settings route")
    data object RulesScreenRoute : NavRoutes(route = "Rules route")
    data object RatingScreenRoute : NavRoutes(route = "Rating route")
    data object GameScreenRoute : NavRoutes(route = "Game route")
}
