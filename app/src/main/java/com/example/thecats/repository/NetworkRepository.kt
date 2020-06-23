package com.example.thecats.repository

import com.example.thecats.repository.entity.thecatentity.Cat
import com.example.thecats.utils.BASE_URL
import com.example.thecats.utils.HEADER_API_KEY
import com.example.thecats.utils.HEADER_KEY
import com.example.thecats.utils.ITEMS_PER_PAGE
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

enum class NetworkRepository {
    INSTANCE;

    private val api: Api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(BASE_URL)
        .build()
        .create(Api::class.java)

    fun loadPage(page: Int): Single<List<Cat>> {
        return api.loadPage(page = page)
    }
}

interface Api {
    @Headers("$HEADER_KEY: $HEADER_API_KEY")
    @GET("images/search")
    fun loadPage(
        @Query("size") size: String = "thumb",
        @Query("order") order: String = "ASC",
        @Query("limit") limit: Int = ITEMS_PER_PAGE,
        @Query("page") page: Int,
        @Query("format") format: String = "json"
    ): Single<List<Cat>>
}
