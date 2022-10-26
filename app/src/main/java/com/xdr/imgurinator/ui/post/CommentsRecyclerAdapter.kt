package com.xdr.imgurinator.ui.post

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xdr.imgurinator.databinding.AdapterCommentItemBinding
import com.xdr.imgurinator.util.Util
import com.xdr.libimgur.models.Comment

class CommentsRecyclerAdapter :
    ListAdapter<Comment, CommentsRecyclerAdapter.CommentViewHolder>(CommentItemCallback()) {
    private val TAG = "CommentsRecyclerAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterCommentItemBinding.inflate(inflater, parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = getItem(position)
        val parentBinding = holder.binding
        inflateChildren(parentBinding, item)
    }

    private fun inflateChildren(parentBinding: AdapterCommentItemBinding, item: Comment) {
        parentBinding.apply {
            authorNameTv.text = item.author
            commentTv.text = item.comment
            commentTimeTv.text = Util.getTimeDiff(item.datetime)
            if(item.children.isNotEmpty()) {
                repliesLayout.visibility = VISIBLE
                repliesCountTv.text ="+${item.children.size}"
            }
            root.setOnLongClickListener {
                if(childLayout.childCount > 0) {
                    childLayout.visibility =
                        if (childLayout.visibility == GONE) {
                            repliesLayout.visibility = GONE
                            VISIBLE
                        }
                        else {
                            repliesLayout.visibility = VISIBLE
                            GONE
                        }
                    true
                } else false
            }
        }

        for (child in item.children) {
            // inflate child view
            val parentView = parentBinding.childLayout
            val inflater = LayoutInflater.from(parentView.context)
            val childBinding = AdapterCommentItemBinding.inflate(inflater)

            // populate child view
            childBinding.apply {
                authorNameTv.text = child.author
                commentTv.text = child.comment
                commentTimeTv.text = Util.getTimeDiff(child.datetime)
            }
            // attach to parent
            parentView.addView(childBinding.root)
            if (child.children.isNotEmpty()) inflateChildren(childBinding, child)
        }
    }

    class CommentItemCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) =
            oldItem == newItem
    }

    class CommentViewHolder(val binding: AdapterCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}