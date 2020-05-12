package com.dfodor.motionlayout.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dfodor.motionlayout.sample.databinding.MusicBandItemBinding

class MusicBandAdapter(
    private val items: List<MusicBandModel>,
    private val onClick: (ViewHolderValues, MusicBandModel) -> Unit
) : RecyclerView.Adapter<MusicBandAdapter.MusicBandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicBandViewHolder {
        MusicBandItemBinding.inflate(LayoutInflater.from(parent.context), parent, false).also {
            return MusicBandViewHolder(it, onClick)
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MusicBandViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class MusicBandViewHolder(
        private val binding: MusicBandItemBinding,
        private val onClick: (ViewHolderValues, MusicBandModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderValues {

        fun bind(item: MusicBandModel) {
            with(binding) {
                thumbnail.background =
                    ContextCompat.getDrawable(binding.root.context, item.drawableId)
                name.text = binding.root.context.getString(item.name)
                tags.text = binding.root.context.getString(item.tags)

                root.setOnClickListener {
                    onClick(this@MusicBandViewHolder, item)
                }
            }
        }

        override fun getThumbnailHeight() = binding.thumbnail.height
        override fun getY() = binding.root.y.toInt()
    }

    interface ViewHolderValues {
        fun getThumbnailHeight(): Int
        fun getY(): Int
    }
}