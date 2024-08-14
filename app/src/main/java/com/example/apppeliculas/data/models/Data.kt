package com.example.apppeliculas.data.models

data class Data(
    val id: Int,
    val titulo: String,
    val genero: String,
    val sinopsis: String,
    val clasificacion: String,
    val duracion: String,
    val director: String,
    val image: String,

    val created_at: String? = null,
    val updated_at: String? = null
)
