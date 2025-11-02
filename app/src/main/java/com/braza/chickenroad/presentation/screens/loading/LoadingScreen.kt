package com.braza.chickenroad.presentation.screens.loading

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.braza.chickenroad.presentation.common.LoadingContent
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoadingScreen(
    loadEndState: StateFlow<Boolean>,
    onLoadEnd: () -> Unit
) {
    val loadEnd = loadEndState.collectAsStateWithLifecycle()

    LaunchedEffect(loadEnd.value) {
        if(loadEnd.value)
            onLoadEnd()
    }

    LoadingContent()
}