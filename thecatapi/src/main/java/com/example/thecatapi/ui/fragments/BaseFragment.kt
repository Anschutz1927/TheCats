package com.example.thecatapi.ui.fragments

import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.LayoutRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.thecatapi.R
import com.example.thecatapi.mvp.views.MvpView
import com.example.thecatapi.network.HEADER_API_KEY
import com.example.thecatapi.network.HEADER_KEY

private const val REQUEST_PERMISSIONS_CODE = 42

abstract class BaseFragment(@LayoutRes id: Int) : Fragment(id), MvpView.BaseView {

    override fun getLifecycleOwner(): LifecycleOwner = viewLifecycleOwner

    override fun checkPermissions(permissions: Array<String>): Boolean {
        return context?.run {
            for (permission in permissions) {
                if (!isPermissionGranted(this, permission)) return false
            }
            return true
        } ?: false
    }

    override fun requestPermissions(permissions: Array<String>) {
        activity?.run {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS_CODE)
        }
    }

    override fun downloadFile(uri: Uri, name: String) {
        context?.run {
            val manager = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(uri)
                .addRequestHeader(HEADER_KEY, HEADER_API_KEY)
                .setTitle(name)
                .setDescription(this.getString(R.string.downloading_description))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            manager.enqueue(request)
        }
    }

    private fun isPermissionGranted(context: Context, permission: String): Boolean =
        ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
}