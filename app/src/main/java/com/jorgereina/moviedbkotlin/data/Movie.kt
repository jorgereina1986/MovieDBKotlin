package com.jorgereina.moviedbkotlin.data

data class Movie(
    val id: Int,
    val vote_average: Double,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val poster_path: String,
    val name: String
)

data class MovieResponse(
    val results: List<Movie>
)
