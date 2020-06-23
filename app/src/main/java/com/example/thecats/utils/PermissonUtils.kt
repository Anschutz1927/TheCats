package com.example.thecats.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat.checkSelfPermission
import androidx.core.app.ActivityCompat.requestPermissions

const val REQUEST_PERMISSIONS_CODE = 42
private val STORAGE_PERMISSIONS = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

fun checkDownloadPermissions(context: Context): Boolean {
    for (permission in STORAGE_PERMISSIONS) {
        if (checkSelfPermission(context, permission) != PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}

fun requestDownloadPermissions(activity: Activity) {
    requestPermissions(activity, STORAGE_PERMISSIONS, REQUEST_PERMISSIONS_CODE)
}