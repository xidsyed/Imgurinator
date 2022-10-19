package com.xdr.imgurinator

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xdr.imgurinator.databinding.ActivityMainBinding
import com.xdr.imgurinator.ui.stories.StoriesRecyclerAdapter
import com.xdr.imgurinator.ui.stories.StoriesViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val storiesViewModel: StoriesViewModel by viewModels()
    private val storiesRecyclerAdapter = StoriesRecyclerAdapter()
    private var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNav()
        binding.storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.HORIZONTAL, false
            )
            adapter = storiesRecyclerAdapter
        }
    }

    private fun setupNav() {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//                R.id.navigation_hot, R.id.navigation_top))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        storiesViewModel.getStories()
        storiesViewModel.tags.observe(this) {
            storiesRecyclerAdapter.submitList(it)
        }
    }
}