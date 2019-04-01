package com.jorgereina.moviedbkotlin.data

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel

open class CategoryFactory(activity: AppCompatActivity) {

    private var categories = ArrayList<Category>()
    private var popularMovies = ArrayList<Movie>()
    private var trendingMovies = ArrayList<Movie>()
    lateinit var viewModel: MovieViewModel
    var activity = activity
    lateinit var context: Context


    fun getCategories(): List<Category> {

        viewModel = ViewModelProviders.of(activity).get(MovieViewModel::class.java)
        viewModel.getPopularMovies().observe(activity, Observer { movies -> popularMovies.addAll(movies!!) })
        viewModel.getTrendingMovies().observe(activity, Observer { movies -> trendingMovies.addAll(movies!!) })

        val popularMoviesCategory = Category("Popular Movies", popularMovies)
        val trendingMoviesCategory = Category("Trending Movies", trendingMovies)

        categories.add(popularMoviesCategory)
        categories.add(trendingMoviesCategory)
        return categories
    }


}