package com.jorgereina.moviedbkotlin.ui

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter(private val movies: ArrayList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val TAG = MovieAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false) as View
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {

        var movie: Movie = movies[position]
        var path = movie.poster_path

        if (!movie.title.isNullOrEmpty()) {
            holder.movieTitle.text = movie.title
        } else {
            holder.movieTitle.text = movie.name
        }
        if (path.isNullOrEmpty()) {
            holder.moviePoster.setImageResource(R.drawable.ic_movie)

        } else {
            Picasso.get().load(buildUri(path)).placeholder(R.drawable.ic_movie).into(holder.moviePoster)
        }
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieTitle = view.movie_title_tv!!
        val moviePoster = view.movie_poster_iv!!
    }

    private fun buildUri(path: String): String {
        //Image url example: https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
        val SCHEME = "https"
        val AUTHORITY = "image.tmdb.org"
        val PATH_T = "t"
        val PATH_P = "p"
        var newPath = path.substring(1)

        var uri: Uri.Builder = Uri.Builder()
        uri.scheme(SCHEME).authority(AUTHORITY).appendPath(PATH_T).appendPath(PATH_P).appendPath(PictureSize.POSTER.size).appendPath(newPath)
        return uri.build().toString()
    }

    enum class PictureSize(val size: String) {
        THUMB("w92"),
        POSTER("w500"),
        ORIGINAL("original")
    }
}
