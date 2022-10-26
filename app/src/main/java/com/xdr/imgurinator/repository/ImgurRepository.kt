package com.xdr.imgurinator.repository

import com.xdr.libimgur.apis.ImgurClient
import com.xdr.libimgur.models.Comment
import com.xdr.libimgur.models.Image
import com.xdr.libimgur.models.Tag
import com.xdr.libimgur.params.Section

class ImgurRepository {
    private val imgurService = ImgurClient.imgurService

    suspend fun getTopFeed(): List<Image>? {
        val response = imgurService.getGallerySection(Section.TOP)
        return response.body()?.data
    }

    suspend fun getHotFeed(): List<Image>? {
        val response = imgurService.getGallerySection(Section.HOT)
        return response.body()?.data
    }

    suspend fun getTags(): List<Tag>? {
        val response = imgurService.getTags()
        return response.body()?.data?.tags
    }

    suspend fun getTagGallery(tag : String) : List<Image>? {
        val response = imgurService.getTagGallery(tag)
        return response.body()?.data?.items
    }

    suspend fun getComments (albumHash : String) : List<Comment>? {
        val response = imgurService.getComments(albumHash)
        return response.body()?.data
    }

    suspend fun getGalleryImage(albumHash: String) : Image? {
        val response = imgurService.getGalleryImage(albumHash)
        return response.body()?.data
    }
}