package com.jorgereina.moviedbkotlin.data

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel

class CategoryFactory(activity: AppCompatActivity) {

    private var categories = ArrayList<Category>()
    private var popularMovies = ArrayList<Movie>()
    lateinit var viewModel: MovieViewModel
    var activity = activity
    lateinit var context: Context


    fun getCategories(): List<Category> {

        viewModel = ViewModelProviders.of(activity).get(MovieViewModel::class.java)
        viewModel.getPopularMovies().observe(activity, Observer { movies -> popularMovies.addAll(movies!!) }

        )


        val popularMoviesCategory: Category = Category("Popular", popularMovies)

        categories.add(popularMoviesCategory)
        return categories
    }


}