package com.jorgereina.moviedbkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Category
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    val TAG = MainActivity::class.java.simpleName

    private var categories = ArrayList<Category>()
    private var popularMovies = ArrayList<Movie>()
    private var trendingMovies = ArrayList<Movie>()
    private var trendingTvShows = ArrayList<Movie>()
    private var upcomingMovies = ArrayList<Movie>()
    private lateinit var adapter: CategoryAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var viewModel: MovieViewModel
    private lateinit var listener: OnMovieSelectedListener

    companion object {

        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        viewModel = ViewModelProviders.of(activity!!).get(MovieViewModel::class.java)

        if (context is OnMovieSelectedListener) {
            listener = context
        } else {
            throw ClassCastException(context.toString() + " must implement OnMovieSelected.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container,
            false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter(categories, listener)

        movies_rv.layoutManager = layoutManager
        movies_rv.adapter = adapter

        viewModel.getPopularMovies().observe(this, Observer { movies ->
            getMovies(movies, popularMovies, "Popular Movies")
            Log.d(TAG, "Popular Movies")
        })
        viewModel.getTrendingMedia("movie").observe(this, Observer { movies ->
            getMovies(movies, trendingMovies, "Trending Movies")
            Log.d(TAG, "Trending Movies")
        })
        viewModel.getUpcomingMovies().observe(this, Observer { movies ->
            getMovies(movies, upcomingMovies, "Coming Soon")
            Log.d(TAG, "Upcoming Movies")
        })
        viewModel.getTrendingMedia("tv").observe(this, Observer { movies ->
            getMovies(movies, trendingTvShows, "Trending TV Shows")
            Log.d(TAG, "Trending TV Shows")
        })
    }

    private fun getMovies(movies: List<Movie>?, movieList: ArrayList<Movie>, categoryTitle: String) {
        if (movieList.isEmpty()) {
            movieList.addAll(movies!!)
        }
        val category = Category(categoryTitle, movieList)
        if (!categories.contains(category)) {
            categories.add(category)
            adapter.notifyDataSetChanged()
        }
    }

    interface OnMovieSelectedListener {
        fun onMovieClick(position: Int)
        fun onMovieLongClick(position: Int)
    }
}
