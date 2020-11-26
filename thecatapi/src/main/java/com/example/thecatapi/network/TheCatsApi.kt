package com.example.thecatapi.network

import com.example.thecatapi.mvp.models.entities.Cat
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://api.thecatapi.com/v1/"
const val HEADER_KEY = "x-api-key"
const val HEADER_API_KEY = "DEMO-API-KEY"
const val ITEMS_PER_PAGE = 50

interface TheCatsApi {
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