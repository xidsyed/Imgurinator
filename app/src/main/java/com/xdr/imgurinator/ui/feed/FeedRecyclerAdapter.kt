package com.xdr.imgurinator.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.AdapterFeedItemBinding
import com.xdr.libimgur.models.Image

// ListAdapter <T, VH>
class FeedRecyclerAdapter(private val listener : FeedListener) :
    ListAdapter<Image, FeedRecyclerAdapter.FeedViewHolder>(FeedDiffCallback()) {

    // Inflates view and returns VH by passing it the binding of the inflated view
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterFeedItemBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    // Binds data to View
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            imageCaption.text = item.title
            imageView
                .load("https://i.imgur.com/${item.cover}l.jpg") {
                    crossfade(true)
                    error(R.drawable.ic_facepalm)
                }
            root.setOnClickListener { listener.itemClicked(item.id) }
        }
    }

    class FeedDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(
            oldItem: Image,
            newItem: Image
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Image,
            newItem: Image
        ): Boolean = oldItem == newItem
    }

    class FeedViewHolder(val binding: AdapterFeedItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}

interface FeedListener{
    fun itemClicked(albumHash: String)
}

