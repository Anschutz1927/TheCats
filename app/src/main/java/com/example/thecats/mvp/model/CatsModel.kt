package com.example.thecats.mvp.model

import androidx.lifecycle.LiveData
import com.example.thecats.repository.DataRepository
import com.example.thecats.repository.entity.favoriteentity.TheCat
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CatsModel @Inject constructor() {

    private val repository = DataRepository.INSTANCE

    fun getCatsList(page: Int, onNext: Consumer<List<TheCat>>, onError: Consumer<Throwable>) {
        repository.loadPage(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { onNext.accept(it) }, onError)
    }

    fun addFavorite(cat: TheCat): Completable {
        return repository.addFavorite(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getFavorites(): LiveData<List<TheCat>> {
        return repository.getFavorites()
    }

    fun removeFavorite(cat: TheCat): Completable {
        return repository.removeFavorite(cat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}