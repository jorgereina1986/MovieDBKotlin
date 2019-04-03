package com.jorgereina.moviedbkotlin.service

import com.jorgereina.moviedbkotlin.data.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    // https://api.themoviedb.org/3/search/movie?api_key=<API-KEY-HERE>&language=en-US&query=batman&include_adult=false
    @GET("search/movie?language=en-US")
    fun getSearchMoviesAsync(@Query("api_key") apiKey: String, @Query("query") query: String): Deferred<MovieResponse>

    //https://api.themoviedb.org/3/movie/popular?api_key=<<api_key>>&language=en-US&page=1
    @GET("movie/popular?language=en-US&page=1")
    fun getPopularMoviesAsync(@Query("api_key") apiKey: String): Deferred<MovieResponse>

    //https://api.themoviedb.org/3/trending/{mediaType}/day?api_key=<<api_key>>
    //https://api.themoviedb.org/3/trending/movie/day?api_key=fe321b50d58f46c550723750263ad677

    @GET("trending/{mediaType}/day?language=en-US&page=1")
    fun getTrendingMediaAsync(@Path("mediaType") mediaType: String, @Query("api_key") apiKey: String): Deferred<MovieResponse>

    //https://api.themoviedb.org/3/movie/upcoming?api_key=fe321b50d58f46c550723750263ad677&language=en-US&page=1
    @GET("movie/upcoming?language=en-US&page=1")
    fun getUpcomingMovies(@Query("api_key") apiKey: String) : Deferred<MovieResponse>


}
