package com.xdr.imgurinator.ui.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.FragmentFeedBinding

class FeedFragment : Fragment(), FeedListener {
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedTitle: String
    private val feedAdapter = FeedRecyclerAdapter(this).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedTitle = arguments?.getString("feed_title").toString()   // TODO : Convert to enum
        // TODO : Use Kotlin Flow
        if (viewModel.feedTitle != feedTitle || viewModel.feed.value.isNullOrEmpty()) viewModel.getFeed(
            feedTitle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFeedBinding.inflate(inflater)

        binding.apply {
            feedRecyclerView.adapter = feedAdapter
            feedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.feed.observe(viewLifecycleOwner) {
            feedAdapter.submitList(it)
        }

        return binding.root
    }

    override fun itemClicked(albumHash: String) {
        val parentNavController =
            Navigation.findNavController(requireActivity(), R.id.main_navigation_host_fragment)
        val bundle = bundleOf("album_hash" to albumHash)
        parentNavController.navigate(R.id.action_global_postFragment, bundle)
    }
}
