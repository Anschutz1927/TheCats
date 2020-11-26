package com.example.thecatapi.mvp.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecatapi.database.FavoriteDatabase
import com.example.thecatapi.mvp.models.entities.Cat
import com.example.thecatapi.network.TheCatsNetwork
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AllCatsModel @Inject constructor(
    private val databaseDao: FavoriteDatabase.InsertDao,
    private val network: TheCatsNetwork
) {

    private val cats = MutableLiveData<List<Cat>>().apply { value = emptyList() }

    fun getAllCats(): LiveData<List<Cat>> = cats

    fun addFavorite(cat: Cat): Completable =
        Completable.fromAction { databaseDao.add(cat) }
            .subscribeOn(Schedulers.io())

    fun loadCats(page: Int): Completable {
        return Completable.fromSingle(network.loadPage(page)
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterSuccess {
                if (page == 0) cats.value = it
                else cats.value = cats.value?.run { this + it } ?: it
            }).subscribeOn(Schedulers.io())
    }
}