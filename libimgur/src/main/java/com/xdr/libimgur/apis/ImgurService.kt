package com.xdr.libimgur.apis

import com.xdr.libimgur.models.GalleryResponse
import com.xdr.libimgur.models.TagsResponse
import retrofit2.Call
import retrofit2.http.GET

interface ImgurService {
    @GET("gallery/hot?mature=true&album_previews=true") // TODO: Use Path Params
    fun getGallery () : Call<GalleryResponse>

    @GET("tags")
    fun getTags () : Call <TagsResponse>
}