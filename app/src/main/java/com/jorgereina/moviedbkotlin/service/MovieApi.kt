package com.jorgereina.moviedbkotlin.service

import com.jorgereina.moviedbkotlin.data.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key=<API-KEY-HERE>&language=en-US&query=batman&include_adult=false
    @GET("movie?language=en-US")
    fun getSearchMoviesAsync(@Query("api_key") apiKey: String, @Query("query") query: String): Deferred<MovieResponse>

    @GET("movie/popular")
    fun getPopularMoviesAsync(): Deferred<MovieResponse>

    //https://api.themoviedb.org/3/trending/all/day?api_key=<<api_key>>
    @GET("trending/all/day?")
    fun getTrendingMoviesAsync(@Query("api_key") apiKey: String): Deferred<MovieResponse>


}