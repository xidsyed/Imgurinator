package com.xdr.imgurinator.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.libimgur.models.Comment
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repo = ImgurRepository()

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    private val _post = MutableLiveData<Image>()
    val post : LiveData<Image> get() = _post

    fun getComments(albumHash: String) = viewModelScope.launch {
        repo.getComments(albumHash)?.let { _comments.postValue(it) }
    }

    fun getPost(albumHash: String) = viewModelScope.launch{
        repo.getGalleryImage(albumHash)?.let { _post.postValue(it)}
    }

}