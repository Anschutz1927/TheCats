package com.example.thecatapi.network

import com.example.thecatapi.mvp.models.entities.Cat
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TheCatsNetwork @Inject constructor(private val api: TheCatsApi) {
    fun loadPage(page: Int): Single<List<Cat>> = api.loadPage(page = page)
}

