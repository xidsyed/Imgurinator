package com.xdr.imgurinator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xdr.imgurinator.repository.ImgurRepository
import com.xdr.imgurinator.util.State
import com.xdr.libimgur.models.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeFragmentViewModel : ViewModel() {
    private val repo = ImgurRepository()
    val tagsFlow = MutableStateFlow<State<List<Tag>>>(State.Fetching)

    fun getStories() = viewModelScope.launch(Dispatchers.IO) {
        do {
            tagsFlow.emit(
                try {
                    val list: List<Tag>? = repo.getTags()
                    if (list.isNullOrEmpty())
                        State.Error("Something went wrong", null)
                    else State.Fetched(list)
                } catch (e: Exception) {
                    State.Error(e.message, e)
                }
            )
            delay(2000)
        } while (tagsFlow.value is State.Error)
    }
}

