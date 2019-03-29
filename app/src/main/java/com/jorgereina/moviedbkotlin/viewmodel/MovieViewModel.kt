package com.jorgereina.moviedbkotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.jorgereina.moviedbkotlin.BuildConfig
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.data.MovieResponse
import com.jorgereina.moviedbkotlin.service.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel : ViewModel() {

    val TAG = MovieViewModel::class.java.simpleName

    private val searchMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val trendingMovies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val popularMovies: MutableLiveData<List<Movie>> = MutableLiveData()


    fun getSearchMovies(): LiveData<List<Movie>> {
        return searchMovies
    }

    fun setSearchQuery(query: String) {
        loadSearchedMovies(query)
    }

    private fun loadSearchedMovies(query: String) {
        val movieService = ApiFactory.tmdbApi
        CoroutineScope(Dispatchers.Main).launch {
            val searchMoviesRequest = movieService.getSearchMoviesAsync(BuildConfig.TMDB_API_KEY, query)
            getResponse(searchMoviesRequest, searchMovies)
        }
    }

    //TODO: Make this in Kotlin
    fun getPopularMovies(): LiveData<List<Movie>> {
        loadPopularMovies()
        return popularMovies
    }

    private fun loadPopularMovies() {
        val movieService = ApiFactory.tmdbApi
        CoroutineScope(Dispatchers.Main).launch {
            val popularMoviesRequest = movieService.getPopularMoviesAsync(BuildConfig.TMDB_API_KEY)
            getResponse(popularMoviesRequest, popularMovies)
        }
    }

    private suspend fun getResponse(movieRequest: Deferred<MovieResponse>, movies: MutableLiveData<List<Movie>>) {
        try {
            val response = movieRequest.await()
            movies.value = response.results
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        } catch (e: HttpException) {
            Log.d(TAG, e.message)
        }
    }


}