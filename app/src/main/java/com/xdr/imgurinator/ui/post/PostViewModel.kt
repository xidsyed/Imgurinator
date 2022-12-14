package com.xdr.imgurinator.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.imgurinator.util.State
import com.xdr.libimgur.models.Comment
import com.xdr.libimgur.models.Image
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    private val repo = ImgurRepository()

    val comments = MutableStateFlow<State<List<Comment>>>(State.Fetching)
    val post = MutableStateFlow<State<Image>>(State.Fetching)
    private lateinit var albumHash : String

    fun setAlbumHash(hash :String) {
        albumHash = hash
        refresh()
    }

    fun refresh () {
        getComments()
        getPost()
    }

    private fun getComments() = viewModelScope.launch {
        comments.emit(
            try {
                val comments = repo.getComments(albumHash)
                if (comments == null) State.Error("An Unknown Error has Occurred", null)
                else State.Fetched(comments)
            } catch (e: Exception) {
                State.Error(e.message, e)
            }
        )
    }

    private fun getPost() = viewModelScope.launch {
        post.emit(
            try {
                val image = repo.getGalleryImage(albumHash)
                if (image == null) State.Error("An Unknown Error has Occurred", null)
                else State.Fetched(image)
            } catch (e: Exception) {
                State.Error(e.message, e)
            }
        )
    }

}