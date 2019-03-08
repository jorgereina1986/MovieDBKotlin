package com.jorgereina.moviedbkotlin.service

import com.jorgereina.moviedbkotlin.data.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key=<API-KEY-HERE>&language=en-US&query=batman&include_adult=false
    @GET("movie?language=en-US")
    fun getMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Deferred<MovieResponse>
}