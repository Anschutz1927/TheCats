package com.example.thecatapi.mvp.presenters

import com.example.thecatapi.mvp.models.FavoriteCatsModel
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.mvp.views.MvpView
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteCatsPresenter @Inject constructor(private val model: FavoriteCatsModel) :
    BasePresenter<MvpView.FavoriteCatsView>() {

    override fun attachView(view: MvpView.FavoriteCatsView) {
        super.attachView(view)
        view.onCats(model.getFavoriteCats())
    }

    override fun detachView() {
        viewAction { model.getFavoriteCats().removeObservers(it.getLifecycleOwner()) }
        super.detachView()
    }

    fun removeFromFavorites(cat: Cat) {
        viewAction { it.showLoader(true) }
        addDisposable(
            model.removeFavoriteCat(cat)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ hideLoader() }, { handleError(it) })
        )
    }
}