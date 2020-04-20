package com.dfodor.motionlayout.sample.sample_1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dfodor.motionlayout.sample.R
import kotlinx.android.synthetic.main.music_band_item.view.*

class MusicBandAdapter(
    private val items: List<MusicBandModel>,
    private val onClick: (ViewHolderValues, MusicBandModel) -> Unit
) : RecyclerView.Adapter<MusicBandAdapter.MusicBandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicBandViewHolder {
        LayoutInflater.from(parent.context).inflate(R.layout.music_band_item, parent, false).also {
            return MusicBandViewHolder(it, onClick)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MusicBandViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MusicBandViewHolder(
        private val view: View,
        private val onClick: (ViewHolderValues, MusicBandModel) -> Unit
    ) : RecyclerView.ViewHolder(view), ViewHolderValues {

        fun bind(item: MusicBandModel) {
            view.cover.background = ContextCompat.getDrawable(view.context, item.drawableId)
            view.name.text = view.context.getString(item.name)
            view.tags.text = view.context.getString(item.tags)

            view.root.setOnClickListener {
                onClick(this, item)
            }
        }

        override fun getCoverHeight() = view.cover.height
        override fun getY() = view.y.toInt()
    }

    interface ViewHolderValues {
        fun getCoverHeight(): Int
        fun getY(): Int
    }
}