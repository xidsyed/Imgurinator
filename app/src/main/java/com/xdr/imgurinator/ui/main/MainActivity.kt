package com.xdr.imgurinator.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.ActivityMainBinding
import com.xdr.imgurinator.ui.feed.FeedRecyclerAdapter
import com.xdr.imgurinator.ui.stories.StoryActivity

class MainActivity : AppCompatActivity(), StoriesRecyclerAdapter.StoryClickListener {
    private lateinit var binding: ActivityMainBinding
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    private val storiesRecyclerAdapter = StoriesRecyclerAdapter(this)
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
        mainActivityViewModel.getStories()
        mainActivityViewModel.tags.observe(this) {
            storiesRecyclerAdapter.submitList(it)
        }
    }

    override fun onClick(tagName: String, imageUrl : String) {
        val intent = Intent (this, StoryActivity::class.java)
        intent.putExtra("tag", tagName)
        intent.putExtra("image_url", imageUrl)
        startActivity(intent)
    }


}