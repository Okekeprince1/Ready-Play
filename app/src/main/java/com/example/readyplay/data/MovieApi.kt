package com.example.readyplay.data

import com.example.readyplay.data.model.ExternalIDDto
import com.example.readyplay.data.model.FindMovieDto
import com.example.readyplay.data.model.MovieDetailsDto
import com.example.readyplay.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/now_playing")
    suspend fun getMovieList(
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1",
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Path("id") id: String,
        @Query("language") language: String = "en-US",
    ): Response<MovieDetailsDto>

    @GET("movie/{id}/external_ids")
    suspend fun getMovieExternalIds(
        @Path("id") id: String,
    ): Response<ExternalIDDto>

    @GET("find/{external_id}")
    suspend fun findMovie(
        @Path("external_id") externalID: String,
        @Query("external_source") src: String,
    ): Response<FindMovieDto>
}
