package com.xdr.imgurinator.ui.feed

import android.util.Log
import android.view.Display
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.GalleryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val repo = ImgurRepository()

    private val _feed = MutableLiveData<List<GalleryResponse.Data>>()
    val feed: LiveData<List<GalleryResponse.Data>> get() = _feed

    fun getFeed(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (type) {
                "hot" -> _feed.postValue(repo.getHotFeed())
                "top" -> _feed.postValue(repo.getTopFeed())
                else -> Log.e("FEED", "feed type can only be hot or top")
            }
        }
    }
}