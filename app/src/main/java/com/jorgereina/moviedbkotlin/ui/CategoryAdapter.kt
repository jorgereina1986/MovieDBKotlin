package com.jorgereina.moviedbkotlin.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jorgereina.moviedbkotlin.R
import com.jorgereina.moviedbkotlin.data.Category
import com.jorgereina.moviedbkotlin.data.Movie
import com.jorgereina.moviedbkotlin.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.child_rv_item.view.*

class CategoryAdapter(private val categories: ArrayList<Category>) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    lateinit var listener: CategoriesFragment.OnMovieSelectedListener


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CategoryAdapter.CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.child_rv_item, parent, false) as View
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, pos: Int) {
        val category: Category = categories[pos]

        holder.categoryTitle.text = category.title

        val categoryLlm = LinearLayoutManager(holder.categoryRv.context, LinearLayoutManager.HORIZONTAL, false)
        categoryLlm.initialPrefetchItemCount = 2
        movieAdapter = MovieAdapter(category.movies)

        holder.categoryRv.layoutManager = categoryLlm
        holder.categoryRv.adapter = movieAdapter

    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val categoryTitle = itemView.category_title_tv!!
        val categoryRv = itemView.category_rv!!

    }
}
