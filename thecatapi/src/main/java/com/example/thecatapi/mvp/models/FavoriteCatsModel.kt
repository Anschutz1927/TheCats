package com.example.thecatapi.mvp.models

import androidx.lifecycle.LiveData
import com.example.thecatapi.database.FavoriteDatabase
import com.example.thecatapi.mvp.models.entities.Cat
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FavoriteCatsModel @Inject constructor(private val databaseDao: FavoriteDatabase.QueryDao) {

    private val cats = databaseDao.get()

    fun getFavoriteCats(): LiveData<List<Cat>> = cats

    fun removeFavoriteCat(cat: Cat): Completable =
        Completable.fromAction { databaseDao.remove(cat) }
            .subscribeOn(Schedulers.io())
}

