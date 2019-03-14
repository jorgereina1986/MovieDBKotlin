package com.jorgereina.moviedbkotlin.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    private var movies = ArrayList<Movie>()
    private lateinit var adapter: MovieAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        layoutManager = LinearLayoutManager(this)
        adapter = MovieAdapter(movies)
        movies_rv.layoutManager = layoutManager
        movies_rv.adapter = adapter

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu!!.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.getMovieSearched(query)
                viewModel.getMovies().observe(this, Observer {
                    movieList -> movies.addAll(movieList!!)
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, movies[3].title)
                })
            }
        }

        return super.onCreateOptionsMenu(menu)
    }
}
