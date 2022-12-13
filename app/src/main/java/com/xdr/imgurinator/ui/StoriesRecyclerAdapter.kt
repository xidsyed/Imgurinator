package com.xdr.imgurinator.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xdr.imgurinator.databinding.AdapterStoriesItemBinding
import com.xdr.libimgur.models.Tag

class StoriesRecyclerAdapter(private val listener : StoryClickListener) :
    ListAdapter<Tag, StoriesRecyclerAdapter.StoryViewHolder>(StoriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterStoriesItemBinding.inflate(inflater, parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val tag = getItem(position)
        holder.binding.apply {
            storyCaption.text = tag.displayName
            storyImage.load("https://i.imgur.com/${tag.backgroundHash}s.jpg") {
                crossfade(true)
            }
            root.setOnClickListener{
                listener.onClick(tag.displayName!!, "https://i.imgur.com/${tag.backgroundHash}s.jpg")
            }
        }
    }

    class StoryViewHolder(
        val binding: AdapterStoriesItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class StoriesDiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean = oldItem == newItem

    }

    interface StoryClickListener {
        fun onClick(tagName : String, imageUrl:String)
    }
}

