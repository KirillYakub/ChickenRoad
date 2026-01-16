package com.chicken.chickenroad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chicken.chickenroad.presentation.screens.web_view.WebViewScreen
import com.chicken.chickenroad.util.web_view.WebViewRequestsManager

class WebViewActivity : ComponentActivity() {

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        WebViewRequestsManager.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val url = "http://lk.nsq.market/en/tools/testing"

        setContent {
            WebViewScreen(url)
        }
    }
}