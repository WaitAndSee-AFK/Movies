package com.example.movies

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = mutableListOf()
    var onReachEndListener: OnReachEndListener? = null
    var onMovieClickListener: OnMovieClickListener? = null

    fun updateMovies(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        Log.d("MoviesAdapter", "onBindViewHolder $position")
        val movie = movies.get(position)
        Glide.with(holder.itemView)
            .load(movie.poster.url)
            .into(holder.imageViewPoster)

        val rating: Double = movie.rating.kp.toDouble()
        val backgroundID = when {
            rating > 7 -> R.drawable.circle_green
            rating > 5 -> R.drawable.circle_orange
            else -> R.drawable.circle_red
        }
        val drawable = ContextCompat.getDrawable(
            holder.itemView.context,
            backgroundID
        )
        holder.textViewRating.background = drawable
        holder.textViewRating.text = String.format("%.1f", rating)

        if (position >= movies.size - 10) {
            onReachEndListener?.onReachEnd()
        }

        holder.itemView.setOnClickListener {
            onMovieClickListener?.onMovieClick(movie)
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)

    }
}