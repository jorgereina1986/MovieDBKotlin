package com.jorgereina.moviedbkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.jorgereina.moviedbkotlin.service.ApiFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    //https://android.jlelse.eu/android-networking-in-2019-retrofit-with-kotlins-coroutines-aefe82c4d777

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieService = ApiFactory.tmdbApi

        CoroutineScope(Dispatchers.Main).launch {
            val popularMovieRequest = movieService.getMovies(BuildConfig.TMDB_API_KEY, "batman")
            try {
                val response = popularMovieRequest.await()
                Log.d(TAG, response.results[0].title)
                hello_tv.text = response.results[0].title
            } catch (e: Exception) {
                Log.d(TAG, e.message)
            } catch (e: HttpException) {
                Log.d(TAG, e.message)
            }
        }
    }
}
