package com.jorgereina.moviedbkotlin.service

import com.jorgereina.moviedbkotlin.data.MovieResponse
import com.jorgereina.moviedbkotlin.data.VideoResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("search/movie?language=en-US")
    fun getSearchMoviesAsync(@Query("api_key") apiKey: String, @Query("query") query: String): Deferred<MovieResponse>

    @GET("movie/popular?language=en-US&page=1")
    fun getPopularMoviesAsync(@Query("api_key") apiKey: String): Deferred<MovieResponse>

    @GET("trending/{mediaType}/day?language=en-US&page=1")
    fun getTrendingMediaAsync(@Path("mediaType") mediaType: String, @Query("api_key") apiKey: String): Deferred<MovieResponse>

    @GET("movie/upcoming?language=en-US&page=1")
    fun getUpcomingMovies(@Query("api_key") apiKey: String): Deferred<MovieResponse>

    //https://api.themoviedb.org/3/movie/299534/videos?api_key=fe321b50d58f46c550723750263ad677&language=en-US
    @GET("movie/{id}/videos?language=en-US")
    fun getMovieInfoAsync(@Path("id") id: String, @Query("api_key") apiKey: String): Deferred<VideoResponse>
}
