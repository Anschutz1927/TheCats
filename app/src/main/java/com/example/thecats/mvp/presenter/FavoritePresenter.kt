package com.example.thecats.mvp.presenter

import androidx.lifecycle.Observer
import com.example.thecats.mvp.model.CatsModel
import com.example.thecats.mvp.view.MVP
import com.example.thecats.repository.entity.favoriteentity.TheCat
import javax.inject.Inject

class FavoritePresenter @Inject constructor(model: CatsModel) :
    BasePresenter<MVP.FavoriteView>(model) {

    private val itemsObserver = initItemsObserver()

    override fun detachView() {
        super.model.getFavorites().removeObserver(itemsObserver)
        super.detachView()
    }

    fun observeFavorites() {
        super.model.getFavorites().observeForever(itemsObserver)
    }

    fun removeFavorite(cat: TheCat) {
        super.model.removeFavorite(cat).subscribe({ println("removed") }, { super.onError(it) })
    }

    private fun initItemsObserver(): Observer<List<TheCat>> = Observer { view?.onDataLoaded(it) }
}
