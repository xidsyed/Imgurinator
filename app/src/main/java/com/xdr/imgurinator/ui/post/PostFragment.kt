package com.xdr.imgurinator.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.xdr.imgurinator.databinding.FragmentPostBinding
import com.xdr.imgurinator.util.State
import com.xdr.imgurinator.util.Util
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.launch

class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    private val albumHash by lazy { requireArguments().getString("album_hash").toString() }
    private var _binding  : FragmentPostBinding? = null
    private val binding get() = _binding!!
    private val commentsRecyclerAdapter = CommentsRecyclerAdapter()
    private val postItemRecyclerAdapter = PostItemRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(layoutInflater)
        viewModel.setAlbumHash(albumHash)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCommentRecyclerView()
        initPostView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPostView() {
        binding.postItemsRecyclerView.apply {
            adapter = postItemRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.post.collect { displayPosts(it) }
                }
            }
        }
    }


    private fun initCommentRecyclerView() {
        binding.commentsRecyclerView.apply {
            adapter = commentsRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.comments.collect { state ->
                        when (state) {
                            is State.Fetching -> Unit
                            is State.Fetched -> commentsRecyclerAdapter.submitList(state.data)
                            is State.Error -> showSnackBarError()
                        }
                    }
                }
            }
        }
    }


    private fun displayPosts (state : State<Image>) {
        binding.apply {
            when (state) {
                is State.Fetching -> Unit   // TODO : Handle this
                is State.Fetched -> {
                    val item = state.data
                    postItemRecyclerAdapter.submitList(
                        if(item.images.isNullOrEmpty()) listOf(item)
                        else item.images
                    )
                    binding.commentCountTv.text = String.format("%,d", item.commentCount)
                    binding.apply {
                        postTitleImage.load("https://imgur.com/user/${item.accountUrl}/avatar")
                        postTitleTextView.text = item.title
                        pointCountTv.text = String.format("%,d", item.points)
                        postAuthorTv.text = item.accountUrl
                        viewCountTv.text = String.format("%,d", item.views)
                        postTime.text = Util.getTimeDiff(item.datetime)
                    }
                }
                is State.Error -> showSnackBarError()
            }
        }

    }


    private fun showSnackBarError(){
        Snackbar
            .make(binding.root, "Something Went Wrong", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry"){
                viewModel.refresh()
            }.show()
    }

}