package com.example.thecatapi.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.thecatapi.mvp.models.entities.Cat

@Database(entities = [Cat::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun insertDao(): InsertDao
    abstract fun queryDao(): QueryDao

    @Dao
    interface InsertDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun add(cat: Cat)
    }

    @Dao
    interface QueryDao {
        @Query("select * from cat")
        fun get(): LiveData<List<Cat>>

        @Delete
        fun remove(cat: Cat)
    }
}