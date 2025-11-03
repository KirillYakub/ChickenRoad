package com.braza.chickenroad.presentation.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.braza.chickenroad.MainActivity
import com.braza.chickenroad.R
import com.braza.chickenroad.presentation.screens.game.game_process.GameScreen
import com.braza.chickenroad.presentation.screens.game.game_process.components.GameViewModel
import com.braza.chickenroad.presentation.screens.game.levels.LevelsScreen
import com.braza.chickenroad.presentation.screens.game.levels.LevelsViewModel
import com.braza.chickenroad.presentation.screens.loading.LoadingScreen
import com.braza.chickenroad.presentation.screens.loading.LoadingViewModel
import com.braza.chickenroad.presentation.screens.menu.MenuScreen
import com.braza.chickenroad.presentation.screens.rating.RatingScreen
import com.braza.chickenroad.presentation.screens.rating.RatingViewModel
import com.braza.chickenroad.presentation.screens.rules.RulesScreen
import com.braza.chickenroad.presentation.screens.settings.SettingsScreen
import com.braza.chickenroad.presentation.screens.settings.components.SettingsViewModel
import com.braza.chickenroad.presentation.sound.SoundViewModel
import com.braza.chickenroad.util.Constants.GAME_LEVEL_ARG
import com.braza.chickenroad.util.Constants.PRIVACY_URL
import com.braza.chickenroad.util.Constants.TERMS_URL

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun NavGraph(navHostController: NavHostController = rememberNavController()) {
    val stackEntry = navHostController.currentBackStackEntryAsState()
    val currentRoute = stackEntry.value?.destination?.route
    val activity = LocalActivity.current as MainActivity

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
    BackHandler {
        when {
            currentRoute == NavRoutes.MenuScreenRoute.route -> activity.finish()
            currentRoute != NavRoutes.LoadScreenRoute.route -> navHostController.navigateUp()
            else -> return@BackHandler
        }
    }

    val soundViewModel = hiltViewModel<SoundViewModel>()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    soundViewModel.onPlayerActivityLifecycleAction()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    soundViewModel.onPlayerSettingsAction(false)
                }
                else -> { }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.LoadScreenRoute.route,
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
                        popUpTo(navHostController.graph.startDestinationId) {
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
                onBackClick = { navHostController.navigateUp() },
                onRulesClick = {
                    navHostController.navigate(NavRoutes.RulesScreenRoute.route) {
                        launchSingleTop = true
                        currentRoute?.let { popUpTo(it) }
                    }
                }
            )
        }
        composable(route = NavRoutes.RulesScreenRoute.route) {
            RulesScreen(currentRoute) {
                navHostController.navigateUp()
            }
        }
        composable(route = NavRoutes.SettingsScreenRoute.route) {
            val model = hiltViewModel<SettingsViewModel>()
            SettingsScreen(
                state = model.state,
                onBackClick = { navHostController.navigateUp() },
                onRulesClick = {
                    navHostController.navigate(NavRoutes.RulesScreenRoute.route) {
                        launchSingleTop = true
                        currentRoute?.let { popUpTo(it) }
                    }
                },
                onPrivacyClick = { model.onCustomTabsOpenUrl(PRIVACY_URL) },
                onTermsClick = { model.onCustomTabsOpenUrl(TERMS_URL) },
                onMusicClick = { isEnabled ->
                    soundViewModel.onPlayerSettingsAction(isEnabled)
                    model.updateMusicData(isEnabled)
                } ,
                onSoundClick = model::updateSoundData
            )
        }
        composable(
            route = NavRoutes.GameScreenRoute.route + "/{$GAME_LEVEL_ARG}",
            arguments = listOf(
                navArgument(GAME_LEVEL_ARG) {
                    type = NavType.IntType
                    nullable = false
                }
            )
        ) {
            val model = hiltViewModel<GameViewModel>()
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
                onRulesClick = {
                    navHostController.navigate(NavRoutes.RulesScreenRoute.route) {
                        launchSingleTop = true
                        currentRoute?.let { popUpTo(it) }
                    }
                },
                onLevelSelected = { level ->
                    navHostController.navigate(NavRoutes.GameScreenRoute.route + "/$level") {
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