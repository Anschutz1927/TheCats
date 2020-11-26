package com.example.thecatapi.dagger

import android.content.Context
import androidx.room.Room
import com.example.thecatapi.R
import com.example.thecatapi.database.FavoriteDatabase
import com.example.thecatapi.network.BASE_URL
import com.example.thecatapi.network.TheCatsApi
import com.example.thecatapi.ui.fragments.AllCatsFragment
import com.example.thecatapi.ui.fragments.FavoriteCatsFragment
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Singleton
@Component(modules = [AppComponent.DatabaseModule::class, AppComponent.NetworkModule::class])
interface AppComponent {

    fun inject(fragment: AllCatsFragment)
    fun inject(fragment: FavoriteCatsFragment)

    @Module
    class DatabaseModule(private val ctx: Context) {

        @Singleton
        @Provides
        fun favoriteDatabase(): FavoriteDatabase = Room.databaseBuilder(
            ctx, FavoriteDatabase::class.java, ctx.getString(R.string.database_name)
        ).build()

        @Provides
        fun insertDao(db: FavoriteDatabase): FavoriteDatabase.InsertDao = db.insertDao()

        @Provides
        fun queryDao(db: FavoriteDatabase): FavoriteDatabase.QueryDao = db.queryDao()
    }

    @Module
    class NetworkModule {

        @Provides
        fun network(): TheCatsApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .baseUrl(BASE_URL)
            .build()
            .create(TheCatsApi::class.java)
    }
}
