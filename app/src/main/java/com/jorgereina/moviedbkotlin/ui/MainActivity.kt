package com.jorgereina.moviedbkotlin.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.widget.SearchView
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

    val TAG = MainActivity::class.java.simpleName

    private var movies = ArrayList<Movie>()
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(movies)
        movies_rv.layoutManager = layoutManager
        movies_rv.adapter = adapter

    }

    private fun loadMovies(query: String) {
        val movieService = ApiFactory.tmdbApi
        CoroutineScope(Dispatchers.Main).launch {
            val popularMovieRequest = movieService.getMoviesAsync(BuildConfig.TMDB_API_KEY, query)
            try {
                val response = popularMovieRequest.await()
                movies.addAll(response.results as ArrayList<Movie>)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.d(TAG, e.message)
            } catch (e: HttpException) {
                Log.d(TAG, e.message)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu!!.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                loadMovies(query)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }
}
