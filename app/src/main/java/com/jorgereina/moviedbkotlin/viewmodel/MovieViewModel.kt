package com.jorgereina.moviedbkotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.jorgereina.moviedbkotlin.BuildConfig
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.service.ApiFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieViewModel: ViewModel() {

    val TAG = MovieViewModel::class.java.simpleName

    private val movies: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovies(): LiveData<List<Movie>> {
        return movies
    }

    fun getMovieSearched(query: String) {
        loadMovies(query)
    }

    private fun loadMovies(query: String) {
        val movieService = ApiFactory.tmdbApi
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovieRequest = movieService.getMoviesAsync(BuildConfig.TMDB_API_KEY, query)
            try {
                val response = popularMovieRequest.await()
                movies.value = response.results
            } catch (e: Exception) {
                Log.d(TAG, e.message)
            } catch (e: HttpException) {
                Log.d(TAG, e.message)
            }
        }
    }
}