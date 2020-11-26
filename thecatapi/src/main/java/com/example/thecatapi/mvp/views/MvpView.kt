package com.example.thecatapi.mvp.views

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.thecatapi.mvp.models.entities.Cat

interface MvpView {

    interface BaseView {
        fun getLifecycleOwner(): LifecycleOwner
        fun checkPermissions(permissions: Array<String>): Boolean
        fun requestPermissions(permissions: Array<String>)
        fun downloadFile(uri: Uri, name: String)
        fun onCats(cats: LiveData<List<Cat>>)
        fun showLoader(isShow: Boolean)
    }

    interface AllCatsView : BaseView {
        fun onItemClicked(cat: Cat)
    }

    interface FavoriteCatsView : BaseView
}