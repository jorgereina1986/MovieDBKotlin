package com.jorgereina.moviedbkotlin.data

data class Video(
    val id: String,
    val key: String
)

data class VideoResponse(
    val id: Int,
    val videos: List<Video>
)
