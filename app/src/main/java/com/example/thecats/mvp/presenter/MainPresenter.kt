package com.example.thecats.mvp.presenter

import com.example.thecats.mvp.model.CatsModel
import com.example.thecats.mvp.view.MVP
import com.example.thecats.repository.entity.favoriteentity.TheCat
import io.reactivex.rxjava3.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainPresenter @Inject constructor(model: CatsModel) : BasePresenter<MVP.MainView>(model) {
    private var cachedData: MutableList<TheCat>? = null

    override fun attachView(view: MVP.MainView) {
        super.attachView(view)
        notifyViewDataReceiver()
    }

    fun requestCats() {
        if (cachedData == null) {
            loadCats()
        }
    }

    fun loadCats() {
        loadCatsPage(0, Consumer { onDataLoaded(it) })
    }

    fun loadPage(page: Int) {
        loadCatsPage(page, Consumer { onPageLoaded(it) })
    }

    fun addFavorite(cat: TheCat) {
        super.model.addFavorite(cat)
            .subscribe({ println("Added to favorites") }, { super.onError(it) })
    }

    private fun loadCatsPage(page: Int, onNext: Consumer<List<TheCat>>) {
        super.view?.onLoadingStateChanged(true)
        super.model.getCatsList(page, onNext, Consumer { super.onError(it) })
    }

    private fun onDataLoaded(data: List<TheCat>) {
        this.cachedData = data.toMutableList()
        notifyViewDataReceiver()
    }

    private fun onPageLoaded(data: List<TheCat>?) {
        if (data != null) {
            this.cachedData?.addAll(data)
        }
        notifyViewPageReceiver(data)
    }

    private fun notifyViewDataReceiver() {
        super.view?.let {
            it.onLoadingStateChanged(false)
            it.onDataLoaded(cachedData)
        }
    }

    private fun notifyViewPageReceiver(data: List<TheCat>?) {
        super.view?.let {
            it.onLoadingStateChanged(false)
            it.onPageLoaded(data)
        }
    }
}
