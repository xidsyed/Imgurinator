package com.xdr.imgurinator.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.FeedListItemBinding
import com.xdr.libimgur.models.GalleryResponse
import com.xdr.libimgur.models.Image

// ListAdapter <T, VH>
class FeedRecyclerAdapter() :
    ListAdapter<Image, FeedRecyclerAdapter.FeedViewHolder>(FeedDiffCallback()) {

    // Inflates view and returns VH by passing it the binding of the inflated view
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FeedViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeedListItemBinding.inflate(inflater, parent, false)
        return FeedViewHolder(binding)
    }

    // Binds data to View
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            imageCaption.text = item.title
            imageView
                .load("https://i.imgur.com/${item.cover}.jpg") {
                    crossfade(true)
                    
                    error(R.drawable.ic_facepalm)
                }
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

    class FeedViewHolder(val binding: FeedListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}