package com.example.thecats.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thecats.repository.entity.favoriteentity.TheCat

@Database(entities = [TheCat::class], version = 1, exportSchema = false)
abstract class StorageRepository : RoomDatabase() {

    companion object {
        lateinit var INSTANCE: StorageRepository

        fun init(context: Context) {
            INSTANCE = Room.databaseBuilder(
                context,
                StorageRepository::class.java,
                StorageRepository::class.java.simpleName
            ).build()
        }
    }

    abstract fun dao(): CatDao

    @Dao
    interface CatDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun addFavorite(cat: TheCat)

        @Query("select * from the_cat")
        fun getFavorites(): LiveData<List<TheCat>>

        @Delete
        fun removeFavorite(cat: TheCat)
    }
}
