package com.example.thecatapi.mvp.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat")
data class Cat(
    @PrimaryKey
    val id: String,
    val url: String,
    val width: Int,
    val height: Int
)