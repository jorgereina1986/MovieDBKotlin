package com.jorgereina.moviedbkotlin.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(private val movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false) as View
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {

        var movie: Movie = movies[position]

        holder.movieTitle.text = movie.title
//        Picasso.get().load(movie.)
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val movieTitle = view.movie_title_tv
        val moviePoster = view.movie_poster_iv
    }


}