package com.chicken.chickenroad.util.web_view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.chicken.chickenroad.util.Constants.PERMISSION_REQUEST_CODE
import java.io.File

object WebViewRequestsManager {

    private var activePermissionRequest: PermissionRequest? = null

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode != PERMISSION_REQUEST_CODE) return
        val req = activePermissionRequest ?: return

        val granted = grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED
        if (granted) req.grant(req.resources) else req.deny()

        activePermissionRequest = null
    }

    fun handlePermissionRequest(activity: Activity?, request: PermissionRequest?) {
        if (activity == null || request == null) {
            request?.deny()
            return
        }
        val needsCamera = request.resources.contains(PermissionRequest.RESOURCE_VIDEO_CAPTURE)

        if (!needsCamera) {
            request.grant(request.resources)
            return
        }

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            request.grant(request.resources)
        } else {
            activePermissionRequest = request
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    fun createFilePickerIntent(fileChooserParams: WebChromeClient.FileChooserParams?): Intent {
        val mimeTypes = fileChooserParams
            ?.acceptTypes
            ?.filter { it.isNotBlank() }
            ?.toTypedArray()

        return Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = mimeTypes?.firstOrNull() ?: "*/*"
            if (!mimeTypes.isNullOrEmpty()) {
                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
    }

    fun createCaptureIntent(context: Context): Pair<Intent?, Uri?> {
        return try {
            val dir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) ?: context.cacheDir
            val imageFile = File.createTempFile("capture_", ".jpg", dir)
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                imageFile
            )
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                putExtra(MediaStore.EXTRA_OUTPUT, uri)
            }
            intent to uri
        } catch (_: Exception) {
            null to null
        }
    }
}