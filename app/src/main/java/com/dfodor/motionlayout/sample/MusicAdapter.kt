package com.dfodor.motionlayout.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.music_band_item.view.*

class MusicAdapter(
    private val items: List<MusicBand>,
    private val onClick: (View, MusicBand) -> Unit
) :
    RecyclerView.Adapter<MusicAdapter.MusicBandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicBandViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.music_band_item, parent, false).also {
            return MusicBandViewHolder(it, onClick)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MusicBandViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MusicBandViewHolder(
        private val view: View,
        private val onClick: (View, MusicBand) -> Unit
    ) :
        RecyclerView.ViewHolder(view) {

        fun bind(item: MusicBand) {
            view.root.background = ContextCompat.getDrawable(view.context, item.drawableId)
            view.name.text = item.name
            view.tags.text = item.tags

            view.root.setOnClickListener {
                onClick(it, item)
            }
        }
    }
}