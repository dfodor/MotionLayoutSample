package com.dfodor.motionlayout.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(
    private val items: List<MovieItem>,
    private val onClick: (View, MovieItem) -> Unit
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false).also {
            return MovieViewHolder(it, onClick)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MovieViewHolder(
        private val view: View,
        private val onClick: (View, MovieItem) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {

        fun bind(item: MovieItem) {
            view.root.background = ContextCompat.getDrawable(view.context, item.drawableId)
            view.movie_title.text = item.title
            view.movie_tags.text = item.tags

            view.root.setOnClickListener {
                onClick(it, item)
            }
        }
    }
}