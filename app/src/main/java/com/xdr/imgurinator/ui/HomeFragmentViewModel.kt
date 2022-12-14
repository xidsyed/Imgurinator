package com.xdr.imgurinator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel(){
    private val repo = ImgurRepository()
    private val _tags = MutableLiveData<List<Tag>>()
    val tags : LiveData<List<Tag>> get() = _tags

    fun getStories() = viewModelScope.launch(Dispatchers.IO) {
        _tags.postValue(repo.getTags())
    }
}