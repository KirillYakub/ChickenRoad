package com.braza.chickenroad.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.common.LoadingContent
import com.braza.chickenroad.presentation.screens.game.game_process.GameScreen
import com.braza.chickenroad.presentation.screens.game.game_process.components.GameViewModel
import com.braza.chickenroad.presentation.screens.game.game_result.GameResultScreen
import com.braza.chickenroad.presentation.screens.game.levels.LevelsScreen
import com.braza.chickenroad.presentation.screens.game.levels.LevelsViewModel
import com.braza.chickenroad.presentation.screens.loading.LoadingScreen
import com.braza.chickenroad.presentation.screens.loading.LoadingViewModel
import com.braza.chickenroad.presentation.screens.menu.MenuScreen
import com.braza.chickenroad.presentation.screens.rating.RatingScreen
import com.braza.chickenroad.presentation.screens.rating.RatingViewModel
import com.braza.chickenroad.presentation.screens.settings.SettingsScreen
import com.braza.chickenroad.presentation.screens.settings.components.SettingsViewModel
import com.braza.chickenroad.util.Constants.PRIVACY_URL
import com.braza.chickenroad.util.Constants.TERMS_URL

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NavGraph(navHostController: NavHostController = rememberNavController()) {
    val stackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = stackEntry.value?.destination?.route

    Image(
        painter = painterResource(id =
            if(currentRoute == NavRoutes.GameScreenRoute.route)
                R.drawable.bg_blur
            else
                R.drawable.bg
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.GameScreenRoute.route,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        composable(route = NavRoutes.LoadScreenRoute.route) {
            val model = hiltViewModel<LoadingViewModel>()
            LoadingScreen(
                loadEndState = model.loadEndState,
                onLoadEnd = {
                    navHostController.navigate(NavRoutes.MenuScreenRoute.route) {
                        launchSingleTop = true
                        popUpTo(NavRoutes.LoadScreenRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = NavRoutes.MenuScreenRoute.route) {
            MenuScreen { route ->
                navHostController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(NavRoutes.MenuScreenRoute.route)
                }
            }
        }
        composable(route = NavRoutes.RatingScreenRoute.route) {
            val model = hiltViewModel<RatingViewModel>()
            RatingScreen(
                leadersList = model.leadersList,
                isLoading = model.isLoading,
                onBackClick = { navHostController.navigateUp() }
            )
        }
        composable(route = NavRoutes.SettingsScreenRoute.route) {
            val model = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                state = model.state,
                onBackClick = { navHostController.navigateUp() },
                onPrivacyClick = { model.onCustomTabsOpenUrl(PRIVACY_URL) },
                onTermsClick = { model.onCustomTabsOpenUrl(TERMS_URL) },
                onMusicClick = model::updateMusicData,
                onSoundClick = model::updateSoundData
            )
        }
        composable(route = NavRoutes.GameScreenRoute.route) {
            val model = viewModel<GameViewModel>()
            GameScreen(
                model = model,
                onSettingsClick = {
                    navHostController.navigate(NavRoutes.SettingsScreenRoute.route) {
                        launchSingleTop = true
                        popUpTo(NavRoutes.GameScreenRoute.route)
                    }
                },
                onHomeClick = { navHostController.navigateUp() }
            )
        }
        composable(route = NavRoutes.LevelsScreenRoute.route) {
            val model = hiltViewModel<LevelsViewModel>()
            LevelsScreen(
                isLoading = model.isLoading,
                maxOpenedLevel = model.maxOpenedLevel,
                onBackClick = { navHostController.navigateUp() },
                onLevelSelected = { level ->
                    navHostController.navigate(NavRoutes.GameScreenRoute.route) {
                        launchSingleTop = true
                        popUpTo(NavRoutes.LevelsScreenRoute.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}