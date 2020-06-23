package com.example.thecats.repository.entity.favoriteentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "the_cat")
data class TheCat(
    @PrimaryKey val id: String,
    val url: String,
    val width: Int,
    val height: Int
)