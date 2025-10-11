package com.example.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrailersAdapter : RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder>() {
    var onTrailerClickListener: OnTrailerClickListener? = null

    private var trailers: List<Trailer> = mutableListOf()

    fun updateTrailers(newTrailers: List<Trailer>) {
        trailers = newTrailers
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrailerViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.trailer_item,
            parent,
            false
        )
        return TrailerViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: TrailerViewHolder,
        position: Int
    ) {
        val trailer = trailers[position]
        holder.textViewTrailer.text = trailer.name

        holder.itemView.setOnClickListener {
            onTrailerClickListener?.onTrailerClick(trailer)
        }
    }

    interface OnTrailerClickListener {
        fun onTrailerClick(trailer: Trailer)
    }

    inner class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTrailer: TextView = itemView.findViewById(R.id.textViewTrailer)
    }
}