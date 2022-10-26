package com.xdr.imgurinator.ui.stories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryViewModel : ViewModel() {

    private val repo = ImgurRepository()

    private val _storiesList = MutableLiveData<List<Image>>()
    val storiesList: LiveData<List<Image>> get() = _storiesList

    fun getStories(tagName: String) = viewModelScope.launch(Dispatchers.IO) {
        repo.getTagGallery(tagName)?.let { _storiesList.postValue(it) }
    }

}