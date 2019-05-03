package com.jorgereina.moviedbkotlin.ui

import android.app.Activity
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.youtube.player.YouTubeIntents
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel


class MainActivity : AppCompatActivity(), CategoriesFragment.OnMovieSelectedListener {

    val TAG = MainActivity::class.java.simpleName

    private val SELECT_VIDEO_REQUEST = 1000

    private var searchMovies = ArrayList<Movie>()
    private lateinit var adapter: CategoryAdapter
    private lateinit var viewModel: MovieViewModel
    private lateinit var videoId: String


    override fun onMovieClick(position: Int) {
        Log.d(TAG, "onMovieClick: $position")
        Toast.makeText(applicationContext, "movie position is $position", Toast.LENGTH_LONG).show()
    }

    override fun onMovieLongClick(id: Int) {
        Log.d(TAG, "onMovieLongClick: $id")
        Toast.makeText(applicationContext, "movie position is $id", Toast.LENGTH_LONG).show()

        viewModel.getMovieUrl(id.toString()).observe(this, Observer { q ->
//            videoId = q!!
            Log.d(TAG, "onMovieLongClick: $q")

            val bigintent = YouTubeIntents.createPlayVideoIntentWithOptions(this, q, true, false)
            startActivity(bigintent)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                SELECT_VIDEO_REQUEST -> {
                    intent = YouTubeIntents.createUploadIntent(this, data!!.data)
                    startActivity(intent)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, CategoriesFragment.newInstance(), "categories")
                .commit()
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
                viewModel.setSearchQuery(query)
                viewModel.getSearchMovies().observe(this, Observer { movies ->
                    searchMovies.addAll(movies!!)
                    adapter.notifyDataSetChanged()
                    Log.d(TAG, searchMovies[3].title)
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }
}
