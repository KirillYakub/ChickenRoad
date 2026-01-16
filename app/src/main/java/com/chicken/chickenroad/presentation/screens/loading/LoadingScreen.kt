package com.chicken.chickenroad.presentation.screens.loading

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chicken.chickenroad.MainActivity
import com.chicken.chickenroad.WebViewActivity
import com.chicken.chickenroad.presentation.common.LoadingContent
import com.chicken.chickenroad.presentation.screens.loading.components.LoadState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoadingScreen(
    loadEndState: StateFlow<LoadState>,
    onLoadEnd: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as MainActivity
    val loadEnd by loadEndState.collectAsStateWithLifecycle()

    LaunchedEffect(loadEnd) {
        when {
            loadEnd.openWebView -> {
                Intent(context, WebViewActivity::class.java).apply {
                    activity.startActivity(this)
                    activity.finish()
                }
            }
            loadEnd.openGame -> onLoadEnd()
        }
    }

    LoadingContent()
}