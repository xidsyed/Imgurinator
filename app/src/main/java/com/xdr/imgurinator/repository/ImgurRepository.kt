package com.xdr.imgurinator.repository

import com.xdr.libimgur.apis.ImgurClient
import com.xdr.libimgur.models.GalleryResponse
import com.xdr.libimgur.models.Image
import com.xdr.libimgur.params.Section

class ImgurRepository {
    private     val imgurService = ImgurClient.imgurService

    suspend fun getTopFeed(): List<GalleryResponse.Data>? {
        val response = imgurService.getGallery(Section.TOP)
        return response.body()?.data
    }

    suspend fun getHotFeed(): List<GalleryResponse.Data>? {
        val response = imgurService.getGallery(Section.HOT)
        return response.body()?.data
    }
}