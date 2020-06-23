package com.example.thecats.mvp.presenter

import com.example.thecats.mvp.model.CatsModel
import com.example.thecats.mvp.view.MVP
import com.example.thecats.utils.convertError

abstract class BasePresenter<MvpView : MVP.BaseView>(protected val model: CatsModel) {

    protected var view: MvpView? = null

    open fun attachView(view: MvpView) {
        this.view = view
    }

    open fun detachView() {
        view = null
    }

    fun onError(t: Throwable) {
        t.printStackTrace()
        view?.let {
            it.onLoadingStateChanged(false)
            it.onError(convertError(t))
        }
    }
}
