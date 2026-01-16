package com.chicken.chickenroad.util.web_view

import android.R
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Message
import android.webkit.CookieManager
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.net.toUri
import com.chicken.chickenroad.util.web_view.WebViewRequestsManager.createCaptureIntent
import com.chicken.chickenroad.util.web_view.WebViewRequestsManager.createFilePickerIntent

@SuppressLint("SetJavaScriptEnabled")
fun createWebView(
    context: Context,
    popupContainer: FrameLayout,
    onPopupCreated: (WebView) -> Unit,
    onPopupClosed: (WebView) -> Unit,
    onFilePicker: (Intent, ValueCallback<Array<Uri>>, Uri?) -> Unit,
): WebView {
    val webView = WebView(context)
    CookieManager.getInstance().apply {
        setAcceptCookie(true)
        setAcceptThirdPartyCookies(webView, true)
        flush()
    }
    with(webView.settings) {
        javaScriptEnabled = true
        javaScriptCanOpenWindowsAutomatically = true
        allowFileAccess = true
        mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        loadWithOverviewMode = true
        useWideViewPort = true
        domStorageEnabled = true
        databaseEnabled = true
        cacheMode = WebSettings.LOAD_DEFAULT
        builtInZoomControls = false
        displayZoomControls = false
        allowContentAccess = true
        mediaPlaybackRequiresUserGesture = false
        userAgentString = userAgentString.replace("; wv", "")
        setSupportZoom(true)
        setSupportMultipleWindows(true)
    }
    webView.webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val targetUri = request?.url ?: return false
            return handleUrlOverride(view, context, targetUri)
        }
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            url: String?
        ): Boolean {
            val targetUri = url?.toUri() ?: return false
            return handleUrlOverride(view, context, targetUri)
        }
        private fun handleUrlOverride(
            view: WebView?,
            context: Context,
            targetUri: Uri
        ): Boolean {
            val scheme = targetUri.scheme?.lowercase().orEmpty()
            val url = targetUri.toString()

            if (view?.tag == "popup") return false

            if (scheme == "tel") {
                context.startActivity(Intent(Intent.ACTION_DIAL, targetUri))
                return true
            }
            if (scheme == "sms" || scheme == "smsto") {
                context.startActivity(Intent(Intent.ACTION_SENDTO, targetUri))
                return true
            }
            if (scheme == "mailto") {
                context.startActivity(Intent(Intent.ACTION_SENDTO, targetUri))
                return true
            }
            if (scheme == "intent") {
                return try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    context.startActivity(intent)
                    true
                } catch (_: ActivityNotFoundException) {
                    val fallbackUrl = try {
                        Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            .getStringExtra("browser_fallback_url")
                    } catch (_: Exception) {
                        null
                    }
                    if (fallbackUrl != null) {
                        view?.loadUrl(fallbackUrl)
                        return true
                    }
                    false
                } catch (_: Exception) {
                    false
                }
            }
            if (scheme in setOf("viber", "tg", "whatsapp")) {
                return try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, targetUri))
                    true
                } catch (_: Exception) {
                    false
                }
            }
            if (scheme == "googlepay" || url.contains("gpay") || scheme == "samsungpay") {
                return try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, targetUri))
                    true
                } catch (_: Exception) {
                    false
                }
            }
            if (scheme.isNotBlank() && scheme !in listOf("http", "https")) {
                return try {
                    context.startActivity(Intent(Intent.ACTION_VIEW, targetUri))
                    true
                } catch (_: Exception) {
                    false
                }
            }
            return false
        }
    }
    webView.webChromeClient = object : WebChromeClient() {
        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            val popupWebView = createWebView(
                context,
                popupContainer, onPopupCreated,
                onPopupClosed, onFilePicker,
            ).apply { tag = "popup" }

            popupContainer.addView(
                popupWebView,
                FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
            )
            onPopupCreated(popupWebView)

            val transport = resultMsg?.obj as? WebView.WebViewTransport ?: return false
            transport.webView = popupWebView
            resultMsg.sendToTarget()
            return true
        }
        override fun onCloseWindow(window: WebView?) {
            window?.let {
                popupContainer.removeView(it)
                onPopupClosed(it)
                it.destroy()
            }
        }
        override fun onPermissionRequest(request: PermissionRequest?) {
            WebViewRequestsManager.handlePermissionRequest(context as? Activity, request)
        }
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.ok) { _, _ -> result?.confirm() }
                .setOnCancelListener { result?.cancel() }
                .show()
            return true
        }
        override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.ok) { _, _ -> result?.confirm() }
                .setNegativeButton(R.string.cancel) { _, _ -> result?.cancel() }
                .setOnCancelListener { result?.cancel() }
                .show()
            return true
        }
        override fun onJsPrompt(
            view: WebView?, url: String?,
            message: String?, defaultValue: String?,
            result: JsPromptResult?
        ): Boolean {
            val input = EditText(context).apply { setText(defaultValue.orEmpty()) }
            AlertDialog.Builder(context)
                .setMessage(message)
                .setView(input)
                .setPositiveButton(R.string.ok) { _, _ -> result?.confirm(input.text.toString()) }
                .setNegativeButton(R.string.cancel) { _, _ -> result?.cancel() }
                .show()
            return true
        }
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            val captureIntent = createCaptureIntent(context)
            val captureUri = captureIntent.second

            val chooser = Intent(Intent.ACTION_CHOOSER).apply {
                putExtra(Intent.EXTRA_INTENT, createFilePickerIntent(fileChooserParams))
                putExtra(Intent.EXTRA_TITLE, "Select or capture file")
                captureIntent.first?.let {
                    putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(it))
                }
            }
            return if (filePathCallback != null) {
                onFilePicker(chooser, filePathCallback, captureUri)
                true
            } else false
        }
    }
    return webView
}