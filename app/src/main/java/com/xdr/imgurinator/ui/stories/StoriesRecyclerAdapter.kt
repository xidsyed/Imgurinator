package com.xdr.imgurinator.ui.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xdr.imgurinator.databinding.StoryListItemBinding
import com.xdr.libimgur.models.Tag

class StoriesRecyclerAdapter() :
    ListAdapter<Tag, StoriesRecyclerAdapter.StoryViewHolder>(StoriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StoryListItemBinding.inflate(inflater, parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val tag = getItem(position)
        holder.binding.apply {
            storyCaption.text = tag.displayName
            storyImage.load("https://i.imgur.com/${tag.backgroundHash}.jpg") {
                crossfade(true)
            }
        }
    }

    class StoryViewHolder(
        val binding: StoryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class StoriesDiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean = oldItem == newItem

    }
}