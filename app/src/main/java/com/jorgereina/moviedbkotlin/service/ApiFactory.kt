package com.jorgereina.moviedbkotlin.service

object ApiFactory{

//    val BASE_URL = "https://api.themoviedb.org/3/search/movie?api_key=fe321b50d58f46c550723750263ad677&language=en-US&query=batman&include_adult=false"
    val BASE_URL = "https://api.themoviedb.org/3/search/"

    val tmdbApi : MovieApi = RetrofitFactory.retrofit(BASE_URL)
        .create(MovieApi::class.java)
}