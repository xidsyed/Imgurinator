package com.xdr.imgurinator.ui.stories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.Gallery
import com.xdr.libimgur.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoriesViewModel : ViewModel() {
    private val repo = ImgurRepository()
    private val _tags = MutableLiveData<List<Tag>>()
    val tags : LiveData<List<Tag>> get() = _tags

    fun getStories() = viewModelScope.launch(Dispatchers.IO) {
        _tags.postValue(repo.getTags())
    }
}