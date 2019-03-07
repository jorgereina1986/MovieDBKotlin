package com.jorgereina.moviedbkotlin.data

data class Movie(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val adult: Boolean
)

data class TmdbMovieResponse(
    val results: List<Movie>
)