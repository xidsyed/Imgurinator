package com.xdr.imgurinator.ui.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.xdr.imgurinator.databinding.FragmentStoryBinding

class StoryFragment : Fragment(), PagerClickListener {

    private val storyViewModel: StoryViewModel by viewModels()
    private val storyPagerAdapter = StoryPagerAdapter(this)
    private var _binding: FragmentStoryBinding? = null
    private val binding get() = _binding!!

    private val tagName: String by lazy { arguments?.getString("tag").toString() }
    private val tagImageUrl: String by lazy { arguments?.getString("image_url")!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoryBinding.inflate(inflater)
        storyViewModel.getStories(tagName)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupStoryPager()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val window = requireActivity().window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.storyPager).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }


}