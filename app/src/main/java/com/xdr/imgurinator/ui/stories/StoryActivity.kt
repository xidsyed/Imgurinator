package com.xdr.imgurinator.ui.stories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager.widget.PagerTitleStrip
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.xdr.imgurinator.R
import com.xdr.imgurinator.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity(), PagerClickListener {

    private val storyViewModel: StoryViewModel by viewModels()
    private val storyPagerAdapter = StoryPagerAdapter(this)
    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupStoryPager()
        // get tagName and url
        val tagName = intent.getStringExtra("tag").toString()
        val tagImageUrl = intent.getStringExtra("image_url")

        storyViewModel.getStories(tagName)
        binding.titleText.text = tagName
        binding.storyImage.load(tagImageUrl) { crossfade(true) }

    }


    override fun onResume() {
        super.onResume()
        hideSystemUi()
        storyViewModel.storiesList.observe(this) {
            storyPagerAdapter.submitList(it)
        }
    }

    override fun toggleOverlay() {
        // hide title layout
        binding.titleLayout.visibility =
            if (binding.titleLayout.visibility == View.VISIBLE)
                View.INVISIBLE
            else View.VISIBLE
        binding.imageCaptionScrollView.visibility =
            if (binding.imageCaptionScrollView.visibility == View.VISIBLE)
                View.INVISIBLE
            else View.VISIBLE
    }

    private fun setupStoryPager() {
        binding.storyPager.adapter = storyPagerAdapter
        binding.storyPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                binding.imageCaption.text = storyPagerAdapter.currentList[position].title
            }
        })
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.storyPager).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}