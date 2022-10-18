package com.xdr.imgurinator.ui.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedTitle : String
    private val feedAdapter = FeedRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedTitle = arguments?.getString("feed_title").toString()   // TODO: Convert to Enum
        viewModel.getFeed(feedTitle)
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
}
