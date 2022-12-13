package com.xdr.imgurinator.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val repo = ImgurRepository()

    var feedTitle = "Hot"
    private val _feed = MutableLiveData<List<Image>>()
    val feed: LiveData<List<Image>> get() = _feed

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