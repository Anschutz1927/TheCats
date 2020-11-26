package com.example.thecatapi.mvp.presenters

import android.annotation.SuppressLint
import com.example.thecatapi.mvp.models.AllCatsModel
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.mvp.views.MvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AllCatsPresenter @Inject constructor(private val model: AllCatsModel) :
    BasePresenter<MvpView.AllCatsView>() {

    override fun attachView(view: MvpView.AllCatsView) {
        super.attachView(view)
        viewAction { it.onCats(model.getAllCats()) }
    }

    override fun detachView() {
        viewAction { model.getAllCats().removeObservers(it.getLifecycleOwner()) }
        super.detachView()
    }

    @SuppressLint("CheckResult")
    fun requestCats(page: Int) {
        showLoader()
        model.loadCats(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ hideLoader() }, { handleError(it) })
    }

    fun addFavorite(cat: Cat) {
        showLoader()
        addDisposable(
            model.addFavorite(cat)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ hideLoader() }, { handleError(it) })
        )
    }

    fun onItemLonClick(cat: Cat): Boolean {
        viewAction { it.onItemClicked(cat) }
        return true
    }
}