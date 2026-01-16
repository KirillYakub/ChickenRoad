package com.chicken.chickenroad.presentation.screens.web_view

import android.app.Activity.RESULT_OK
import androidx.compose.runtime.Composable
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.chicken.chickenroad.util.web_view.createWebView

@Composable
fun WebViewScreen(url: String) {
    val callbackState = remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val captureUriState = remember { mutableStateOf<Uri?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val callback = callbackState.value
        val captureUri = captureUriState.value
        val data = result.data
        val isSuccess = result.resultCode == RESULT_OK
        val clipData = data?.clipData
        val dataUri = data?.data

        val uris: Array<Uri>? = when {
            !isSuccess -> null
            clipData != null -> {
                (0 until clipData.itemCount).mapNotNull { index ->
                    clipData.getItemAt(index).uri
                }.toTypedArray()
            }
            dataUri != null -> arrayOf(dataUri)
            captureUri != null -> arrayOf(captureUri)
            else -> null
        }
        callback?.onReceiveValue(uris)
        callbackState.value = null
        captureUriState.value = null
    }

    Surface(color = MaterialTheme.colorScheme.background) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
                .imePadding()
        ) {
            val context = LocalContext.current

            val lifecycleOwner = LocalLifecycleOwner.current
            val primaryContentState = remember { mutableStateOf<WebView?>(null) }
            val secondaryContentState = remember { mutableStateOf<WebView?>(null) }

            BackHandler {
                when {
                    secondaryContentState.value != null -> {
                        val popup = secondaryContentState.value
                        val parent = popup?.parent as? FrameLayout
                        parent?.removeView(popup)
                        popup?.destroy()
                        secondaryContentState.value = null
                    }
                    primaryContentState.value?.canGoBack() == true -> {
                        primaryContentState.value?.goBack()
                    }
                    else -> { }
                }
            }

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    val popupContainer = FrameLayout(context)
                    val root = FrameLayout(context)

                    val mainWebView = createWebView(
                        context = context,
                        popupContainer = popupContainer,
                        onPopupCreated = { popup -> secondaryContentState.value = popup },
                        onPopupClosed = { popup ->
                            popupContainer.removeView(popup)
                            secondaryContentState.value = null
                        },
                        onFilePicker = { intent, fileCallback, captureUri ->
                            callbackState.value = fileCallback
                            captureUriState.value = captureUri
                            filePickerLauncher.launch(intent)
                        }
                    )
                    primaryContentState.value = mainWebView
                    root.addView(
                        mainWebView,
                        FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                    )
                    root.addView(
                        popupContainer,
                        FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                        )
                    )
                    mainWebView.loadUrl(url)
                    root
                }
            )

            DisposableEffect(lifecycleOwner) {
                val observer = LifecycleEventObserver { _, event ->
                    when (event) {
                        Lifecycle.Event.ON_RESUME -> {
                            primaryContentState.value?.onResume()
                            primaryContentState.value?.resumeTimers()
                            secondaryContentState.value?.onResume()
                            secondaryContentState.value?.resumeTimers()
                        }
                        Lifecycle.Event.ON_PAUSE -> {
                            primaryContentState.value?.onPause()
                            primaryContentState.value?.pauseTimers()
                            secondaryContentState.value?.onPause()
                            secondaryContentState.value?.pauseTimers()
                        }
                        else -> Unit
                    }
                }
                lifecycleOwner.lifecycle.addObserver(observer)
                onDispose {
                    lifecycleOwner.lifecycle.removeObserver(observer)
                }
            }
        }
    }
}