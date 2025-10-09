package com.example.movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import java.time.format.TextStyle

private const val EXTRA_MOVIE = "movie"

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var imageViewPoster: ImageView
    private lateinit var textViewTitle: TextView
    private lateinit var textViewYear: TextView
    private lateinit var textViewDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        initViews()

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
    }

    fun newIntent(context: Context, movie: Movie): Intent {
        val intent  = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(EXTRA_MOVIE, movie)
        return intent
    }

    private fun initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster)
        textViewTitle = findViewById(R.id.textViewTitle)
        textViewYear = findViewById(R.id.textViewYear)
        textViewDescription = findViewById(R.id.textViewDescription)
    }
}