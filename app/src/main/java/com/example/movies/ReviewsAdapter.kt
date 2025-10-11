package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

private const val TYPE_POSITIVE = "Позитивный"
private const val TYPE_NEGATIVE = "Нейтральный"

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
    private var reviews: List<Review> = mutableListOf<Review>()

    fun updateReviews(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.review_item,
            parent,
            false
        )
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ReviewViewHolder,
        position: Int
    ) {
        val review = reviews[position]
        holder.textViewAuthor.text = review.author
        holder.textViewReview.text = review.review
        val backgroundID = when (review.type) {
            TYPE_POSITIVE -> android.R.color.holo_green_light
            TYPE_NEGATIVE -> android.R.color.holo_red_light
            else -> android.R.color.holo_orange_light
        }
        val drawable = ContextCompat.getDrawable(holder.itemView.context, backgroundID)
        holder.linearLayoutContainer.background = drawable
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
        val textViewReview: TextView = itemView.findViewById(R.id.textViewReview)
        val linearLayoutContainer: LinearLayout = itemView.findViewById(R.id.linearLayoutContainer)

    }
}