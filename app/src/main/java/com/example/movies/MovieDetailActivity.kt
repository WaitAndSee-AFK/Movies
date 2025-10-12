package com.example.movies

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.time.format.TextStyle
import androidx.core.net.toUri

private const val EXTRA_MOVIE = "movie"
private const val TAG = "MovieDetailActivity"

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var imageViewPoster: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewDescription: TextView
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var recyclerViewTrailers: RecyclerView
    private lateinit var trailersAdapter: TrailersAdapter
    private lateinit var recyclerViewReviews: RecyclerView
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var imageViewStar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        initViews()
        trailersAdapter = TrailersAdapter()
        reviewsAdapter = ReviewsAdapter()
        recyclerViewTrailers.adapter = trailersAdapter
        recyclerViewReviews.adapter = reviewsAdapter
        installingMovieInformation()
    }

    private fun installingMovieInformation() {
        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        Glide.with(this)
            .load(movie.poster.url)
            .into(imageViewPoster)
        textViewTitle.text = movie.name
        textViewYear.text = movie.year.toString().trim()
        textViewDescription.text = movie.description

        viewModel.loadTrailers(movie.id)
        viewModel.trailers.observe(this, Observer<List<Trailer>> {
            trailers -> trailersAdapter.updateTrailers(trailers)
        })
        trailersAdapter.onTrailerClickListener = object : TrailersAdapter.OnTrailerClickListener {
            override fun onTrailerClick(trailer: Trailer) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(trailer.url.toUri())
                startActivity(intent)
            }
        }

        viewModel.reviews.observe(this, Observer<List<Review>> {
            reviews -> reviewsAdapter.updateReviews(reviews)
        })
        viewModel.loadReviews(movie.id)

        val starOff: Drawable = ContextCompat.getDrawable(
            this@MovieDetailActivity,
            android.R.drawable.star_big_off
        )
        val starOn: Drawable = ContextCompat.getDrawable(
            this@MovieDetailActivity,
            android.R.drawable.star_big_on
        )
        viewModel.getFavouriteMovie(movie.id).observe(this, Observer<Movie?> {
            movieFromDB ->
            if (movieFromDB == null) {
                imageViewStar.setImageDrawable(starOff)
                imageViewStar.setOnClickListener {
                    viewModel.insertMovie(movie)
                }
            } else {
                imageViewStar.setImageDrawable(starOn)
                imageViewStar.setOnClickListener {
                    viewModel.removeMovie(movie.id)
                }
            }
        })
    }

    fun newIntent(context: Context, movie: Movie): Intent {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        return intent
    }

    private fun initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewYear = findViewById(R.id.textViewYear)
        textViewDescription = findViewById(R.id.textViewDescription)
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers)
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews)
        imageViewStar = findViewById(R.id.imageViewStar)
    }
}