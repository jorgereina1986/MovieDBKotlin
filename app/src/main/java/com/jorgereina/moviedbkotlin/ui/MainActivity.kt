package com.jorgereina.moviedbkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.jorgereina.moviedbkotlin.BuildConfig
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.service.ApiFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    //https://android.jlelse.eu/android-networking-in-2019-retrofit-with-kotlins-coroutines-aefe82c4d777

    val TAG = MainActivity::class.java.simpleName

    private var movies = ArrayList<Movie>()
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager : RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(movies)
        movies_rv.layoutManager = layoutManager
        movies_rv.adapter = adapter

        val movieService = ApiFactory.tmdbApi
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovieRequest = movieService.getMoviesAsync(BuildConfig.TMDB_API_KEY, "batman")
            try {
                val response = popularMovieRequest.await()
                Log.d(TAG, response.results[0].title)
                movies.addAll(response.results as ArrayList<Movie>)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                Log.d(TAG, e.message)
            } catch (e: HttpException) {
                Log.d(TAG, e.message)
            }
        }
    }
}
