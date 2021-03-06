package com.jorgereina.moviedbkotlin.ui

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Movie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieAdapter internal constructor
    (private val listener: CategoriesFragment.OnMovieSelectedListener, private val movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    val TAG = MovieAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false) as View
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {

        val movie: Movie = movies[position]
        val path = movie.poster_path

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

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener {

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onMovieLongClick(adapterPosition)
            return true
        }

        override fun onClick(v: View?) {
            listener.onMovieClick(adapterPosition)
        }

        val movieTitle = itemView.movie_title_tv!!
        val moviePoster = itemView.movie_poster_iv!!
    }

    private fun buildUri(path: String): String {
        //Image url example: https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
        val SCHEME = "https"
        val AUTHORITY = "image.tmdb.org"
        val PATH_T = "t"
        val PATH_P = "p"
        val newPath = path.substring(1)

        val uri: Uri.Builder = Uri.Builder()
        uri.scheme(SCHEME).authority(AUTHORITY).appendPath(PATH_T).appendPath(PATH_P)
            .appendPath(PictureSize.POSTER.size).appendPath(newPath)
        return uri.build().toString()
    }

    enum class PictureSize(val size: String) {
        THUMB("w92"),
        POSTER("w500"),
        ORIGINAL("original")
    }
}
