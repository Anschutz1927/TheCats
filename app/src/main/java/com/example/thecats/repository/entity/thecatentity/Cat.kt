package com.example.thecats.repository.entity.thecatentity

data class Cat(
    val breeds: List<Breed>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)