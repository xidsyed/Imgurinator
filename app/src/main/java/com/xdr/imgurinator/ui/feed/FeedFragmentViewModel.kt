package com.xdr.imgurinator.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.imgurinator.util.State
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FeedFragmentViewModel : ViewModel() {
    private val repo = ImgurRepository()

    var feedTitle = "Hot"
    val feed = MutableStateFlow<State<List<Image>>>(State.Fetching)

    fun getFeed(type: String) {
        viewModelScope.launch(Dispatchers.IO) {
            feed.emit(
                try {
                    val list: List<Image>? = when (type) {
                        "hot" -> repo.getHotFeed()
                        else -> repo.getTopFeed()
                    }
                    if (list.isNullOrEmpty()) State.Error("Something went wrong", null)
                    else State.Fetched(list)
                } catch (e: Exception) {
                    State.Error(e.message, e)
                }
            )
        }
    }
}
