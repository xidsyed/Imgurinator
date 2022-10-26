package com.xdr.imgurinator.ui.feed

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xdr.imgurinator.databinding.FragmentFeedBinding
import com.xdr.imgurinator.ui.post.PostActivity

class FeedFragment : Fragment(), FeedListener {
    private val viewModel: FeedViewModel by viewModels()
    private lateinit var feedTitle : String
    private val feedAdapter = FeedRecyclerAdapter(this)

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

    override fun itemClicked(albumHash : String) {
        val intent = Intent(requireContext(), PostActivity::class.java)
        intent.putExtra("album_hash", albumHash)
        startActivity(intent)
    }
}
