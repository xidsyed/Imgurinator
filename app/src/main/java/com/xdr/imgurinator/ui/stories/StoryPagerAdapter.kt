package com.xdr.imgurinator.ui.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.AdapterStoryViewBinding
import com.xdr.libimgur.models.Image

class StoryPagerAdapter(val listener : PagerClickListener) :
    ListAdapter<Image, StoryPagerAdapter.StoryPageViewHolder>(StoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterStoryViewBinding.inflate(inflater, parent, false)
        return StoryPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryPageViewHolder, position: Int) {
        val item = getItem(position)
        var imgUrl = "https://i.imgur.com/${item.cover}l.jpg"

        holder.binding.apply {
            imageView.load(imgUrl) {
                crossfade(true)
                error(R.drawable.ic_facepalm)
            }
            root.setOnClickListener {
                listener.toggleOverlay()
            }
        }
    }

    class StoryPageViewHolder(val binding: AdapterStoryViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    class StoryDiffCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem


        override fun areContentsTheSame(oldItem: Image, newItem: Image) = oldItem == newItem
    }

}
    interface PagerClickListener {
        fun toggleOverlay()
    }
