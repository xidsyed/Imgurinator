package com.xdr.imgurinator.ui.post

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.PostItemBinding
import com.xdr.libimgur.models.Image

class PostItemRecyclerAdapter :
    ListAdapter<Image, PostItemRecyclerAdapter.PostItemViewHolder>(PostItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(inflater, parent, false)
        return PostItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            ivItem.load(item.link) {
                error(R.drawable.ic_facepalm)
            }
            item.description?.let { tvItemDescription.text = it }

        }
    }


    class PostItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }

    class PostItemViewHolder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root)
}