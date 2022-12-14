package com.xdr.imgurinator.ui

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
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.FragmentHomeBinding
import com.xdr.imgurinator.ui.stories.StoriesRecyclerAdapter
import com.xdr.imgurinator.util.State
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), StoriesRecyclerAdapter.StoryClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val storiesRecyclerAdapter = StoriesRecyclerAdapter(this).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentViewContainer =
            childFragmentManager.findFragmentById(R.id.feed_navigation_host_fragment) as? NavHostFragment
        navController = fragmentViewContainer?.navController!!
        setupNav(navController)
        binding.storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL, false
            )
            adapter = storiesRecyclerAdapter
        }
        collectFlows()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.tagsFlow.collect { state ->
                        when (state) {
                            is State.Fetching -> display(spinner = true)
                            is State.Fetched -> {
                                display(recyclerView = true)
                                storiesRecyclerAdapter.submitList(state.data)
                            }
                            is State.Error -> display(textView = true)

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
        textView: Boolean = false,
        recyclerView: Boolean = false
    ) {
        binding.apply {
            this.spinner.visibility = if (spinner) View.VISIBLE else View.GONE
            tvErrorMessage.visibility = if (textView) View.VISIBLE else View.GONE
            storyRecyclerView.visibility = if (recyclerView) View.VISIBLE else View.GONE
        }
    }


    private fun setupNav(navController: NavController) {
        val navView: BottomNavigationView = binding.bottomNavView
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
/*        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_hot, R.id.navigation_top))
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        navView.setupWithNavController(navController)
    }


    override fun onClick(tagName: String, imageUrl: String) {
        val parentNavController =
            findNavController(requireActivity(), R.id.main_navigation_host_fragment)
        val bundle = bundleOf(
            "tag" to tagName,
            "image_url" to imageUrl
        )
        parentNavController.navigate(R.id.action_global_storyFragment, bundle)
    }
}