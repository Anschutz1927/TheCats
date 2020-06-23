package com.example.thecats.repository

import androidx.lifecycle.LiveData
import com.example.thecats.repository.entity.favoriteentity.TheCat
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class DataRepository {

    companion object {
        val INSTANCE = DataRepository()
    }

    private val networkRepository = NetworkRepository.INSTANCE
    private val storageRepository = StorageRepository.INSTANCE

    fun loadPage(page: Int): Single<List<TheCat>> {
        return networkRepository.loadPage(page = page)
            .flatMapObservable { Observable.fromIterable(it) }
            .map { TheCat(it.id, it.url, it.width, it.height) }
            .toList()
    }

    fun addFavorite(cat: TheCat): Completable {
        return Completable.fromAction { storageRepository.dao().addFavorite(cat) }
    }

    fun getFavorites(): LiveData<List<TheCat>> {
        return storageRepository.dao().getFavorites()
    }

    fun removeFavorite(cat: TheCat): Completable {
        return Completable.fromAction { storageRepository.dao().removeFavorite(cat) }
    }
}