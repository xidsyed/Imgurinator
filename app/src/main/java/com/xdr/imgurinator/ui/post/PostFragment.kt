package com.xdr.imgurinator.ui.post

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.ActivityPostBinding
import com.xdr.imgurinator.databinding.FragmentPostBinding
import com.xdr.imgurinator.util.Util

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    private val albumHash by lazy { requireArguments().getString("album_hash").toString() }
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentPostBinding.inflate(layoutInflater)
    }
    private val commentsRecyclerAdapter = CommentsRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        albumHash.let {
            viewModel.getComments(it)
            viewModel.getPost(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initPostView()
    }

    private fun initPostView() {
        viewModel.post.observe(viewLifecycleOwner) { item ->
            binding.commentCountTv.text = String.format("%,d", item.commentCount)
            item.description?.let { binding.postDescriptionTv.text = it }
            binding.postItem.apply {
                postImageView.load("https://i.imgur.com/${item.cover}h.jpg") {
                    crossfade(true)
                    error(R.drawable.ic_facepalm)
                }
                postTitleImage.load("https://imgur.com/user/${item.accountUrl}/avatar")
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

        viewModel.comments.observe(viewLifecycleOwner) {
            commentsRecyclerAdapter.submitList(it)
        }
    }

}