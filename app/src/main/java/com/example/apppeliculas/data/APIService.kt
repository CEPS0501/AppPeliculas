package com.example.apppeliculas.data

import com.example.apppeliculas.data.models.Data
import com.example.apppeliculas.data.models.PeliculasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    @GET("{endpoint}")
    suspend fun listPeliculas(
        @Path("endpoint") endpoint: String,
        @Query("titulo") searchQuery: String? = null
    ): Response<PeliculasResponse>

    @POST("{endpoint}")
    suspend fun savePelicula(@Path("endpoint") endpoint: String, @Body peliculas: Data): Response<Data>

    @PUT("{endpoint}/{id}")
    suspend fun updatePelicula(@Path("endpoint") endpoint: String, @Path("id") id: Int, @Body pelicula: Data ): Response<Data>

    @DELETE("{endpoint}/{id}")
    suspend fun deletePelicula(@Path("endpoint") endpoint: String, @Path("id") id:Int): Response<Void>

    @GET("{endpoint}/{id}")
    suspend fun showPelicula(@Path("endpoint") endpoint: String, @Path("id") id: Int): Response<Data>

}