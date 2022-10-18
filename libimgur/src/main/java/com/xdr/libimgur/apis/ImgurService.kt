package com.xdr.libimgur.apis

import com.xdr.libimgur.models.GalleryResponse
import com.xdr.libimgur.models.TagsResponse
import com.xdr.libimgur.params.Section
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImgurService {
    //  /gallery/hot?mature=true&album_previews=true

    @GET("gallery/{section}")
    suspend fun getGallery(
        @Path("section")
        section: Section,
        @Query("mature")
        mature: Boolean? = true,
        @Query("album_previews")
        albumPreviews: Boolean? = true
    ): Response<GalleryResponse>

    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>

}