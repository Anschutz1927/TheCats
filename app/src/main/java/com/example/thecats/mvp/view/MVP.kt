package com.example.thecats.mvp.view

import com.example.thecats.repository.entity.favoriteentity.TheCat
import com.example.thecats.utils.Error

interface MVP {

    interface BaseView {
        fun onLoadingStateChanged(isLoading: Boolean)
        fun onDataLoaded(data: List<TheCat>?)
        fun onError(error: Error)
    }

    interface MainView : BaseView {
        fun onPageLoaded(data: List<TheCat>?)
    }

    interface FavoriteView : BaseView
}