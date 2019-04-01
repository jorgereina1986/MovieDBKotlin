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
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu

import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Category
import com.jorgereina.moviedbkotlin.data.CategoryFactory
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    val TAG = MainActivity::class.java.simpleName

    private var categories = ArrayList<Category>()
    private var searchMovies = ArrayList<Movie>()
    private var popularMovies = ArrayList<Movie>()
    private var trendingMovies = ArrayList<Movie>()
    private lateinit var adapter: CategoryAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        adapter = CategoryAdapter(categories)

        movies_rv.layoutManager = layoutManager
        movies_rv.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        viewModel.getPopularMovies().observe(this, Observer { movies ->
            if (popularMovies.isEmpty()) {
                popularMovies.addAll(movies!!)
            }
            Log.d(TAG, popularMovies[0].title)
            val popularMoviesCategory = Category("Popular Movies", popularMovies)
            if (!categories.contains(popularMoviesCategory)) {
                categories.add(popularMoviesCategory)
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.getTrendingMovies().observe(this, Observer { movies ->
            if (trendingMovies.isEmpty()) {
                trendingMovies.addAll(movies!!)
            }
            Log.d(TAG, trendingMovies[0].title)
            val trendingMoviesCategory = Category("Trending Movies", trendingMovies)
            if (!categories.contains(trendingMoviesCategory)) {
                categories.add(trendingMoviesCategory)
                adapter.notifyDataSetChanged()
            }
        })



//        layoutManager = LinearLayoutManager(this)
//        adapter = MovieAdapter(searchMovies)




//        viewModel.getPopularMovies().observe(this, Observer { movies -> popularMovies.addAll(movies!!)
//            Log.d(TAG, popularMovies[0].title)
//        })



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
