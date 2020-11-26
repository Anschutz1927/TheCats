package com.example.thecatapi.mvp.presenters

import android.Manifest
import android.net.Uri
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.mvp.views.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View : MvpView.BaseView> {

    private var view: View? = null
    private val disposables: CompositeDisposable = CompositeDisposable()

    protected fun viewAction(action: (view: View) -> Unit) = view?.let { action(it) }

    protected fun addDisposable(vararg disposables: Disposable) =
        this.disposables.addAll(*disposables)

    protected fun handleError(e: Throwable) {
        e.printStackTrace()
        hideLoader()
    }

    protected fun showLoader() = viewAction { it.showLoader(true) }

    protected fun hideLoader() = viewAction { it.showLoader(false) }

    open fun attachView(view: View) {
        this.view = view
    }

    open fun detachView() {
        clearDisposables()
        this.view = null
    }

    fun onDownloadClicked(cat: Cat) = checkDownloadPermissions { result ->
        if (result) downloadFromUri(Uri.parse(cat.url), cat.id)
        else requestDownloadPermissions()
    }

    private fun checkDownloadPermissions(result: (isGranted: Boolean) -> Unit) = viewAction {
        result(it.checkPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)))
    }

    private fun requestDownloadPermissions() = viewAction {
        it.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
    }

    private fun downloadFromUri(uri: Uri, name: String) = viewAction { it.downloadFile(uri, name) }

    private fun clearDisposables() = disposables.clear()
}