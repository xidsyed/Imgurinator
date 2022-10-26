package com.xdr.imgurinator.ui.post

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.ActivityPostBinding
import com.xdr.imgurinator.util.Util

class PostActivity : AppCompatActivity() {
    private val albumHash by lazy { intent.getStringExtra("album_hash") }
    private val viewModel: PostViewModel by viewModels()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private val TAG = "PostActivity"
    private val commentsRecyclerAdapter = CommentsRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        albumHash?.let {
            viewModel.getComments(it)
            viewModel.getPost(it)
        }

        initRecyclerView()
        initPostView()
    }

    private fun initPostView() {
        viewModel.post.observe(this) { item ->
            binding.commentCountTv.text = String.format("%,d", item.commentCount)
            item.description?.let { binding.postDescriptionTv.text = it }
            binding.postItem.apply {
                postImageView.load("https://i.imgur.com/${item.cover}l.jpg") {
                    crossfade(true)
                    error(R.drawable.ic_facepalm)
                }
                postTitleImage.load("https://imgur.com/user/${item.accountUrl}/avatar?maxwidth=290")
                postTitleTextView.text = item.title
                pointCountTv.text = String.format("%,d", item.points)
                postAuthorTv.text = item.accountUrl
                viewCountTv.text = String.format("%,d", item.views)
                postTime.text = Util.getTimeDiff(item.datetime)
            }
        }
    }

    private fun initRecyclerView() {
        binding.commentsRecyclerView.apply {
            adapter = commentsRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.comments.observe(this) {
            commentsRecyclerAdapter.submitList(it)
        }
    }
}