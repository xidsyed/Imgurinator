package com.xdr.imgurinator.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.FragmentFeedBinding
import com.xdr.imgurinator.util.State
import kotlinx.coroutines.launch

class FeedFragment : Fragment(), FeedListener {
    private val viewModel: FeedFragmentViewModel by viewModels()
    private lateinit var feedTitle: String
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val feedAdapter = FeedRecyclerAdapter(this).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedTitle = arguments?.getString("feed_title").toString()   // TODO : Convert to enum
        // TODO : Use Kotlin Flow
        if (viewModel.feedTitle != feedTitle ||
            viewModel.feed.value is State.Fetching ||
            viewModel.feed.value is State.Error
        )
            viewModel.getFeed(feedTitle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater)
        binding.feedRecyclerView.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        collectFlows()

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.feed.collect { state ->
                        when (state) {
                            is State.Fetching -> display(spinner = true)
                            is State.Fetched -> {
                                display(recyclerView = true)
                                feedAdapter.submitList(state.data)
                            }
                            is State.Error -> display(errorImage = true)
                        }
                    }
                }
                // launch another coroutine to collect another flow
            }
        }
    }

    // Set one of these to
    private fun display(
        spinner: Boolean = false,
        errorImage: Boolean = false,
        recyclerView: Boolean = false
    ) {
        binding.apply {
            this.spinner.visibility = if (spinner) View.VISIBLE else View.GONE
            ivNoInternet.visibility = if (errorImage) View.VISIBLE else View.GONE
            feedRecyclerView.visibility = if (recyclerView) View.VISIBLE else View.GONE
        }
    }

    override fun itemClicked(albumHash: String) {
        val parentNavController =
            Navigation.findNavController(requireActivity(), R.id.main_navigation_host_fragment)
        val bundle = bundleOf("album_hash" to albumHash)
        parentNavController.navigate(R.id.action_global_postFragment, bundle)
    }
}
