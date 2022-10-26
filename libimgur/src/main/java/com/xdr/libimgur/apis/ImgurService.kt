package com.xdr.libimgur.apis

import com.xdr.libimgur.models.response.*
import com.xdr.libimgur.params.Section
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Tag

interface ImgurService {
    //  /gallery/hot?mature=true&album_previews=true

    @GET("gallery/{section}")
    suspend fun getGallerySection(
        @Path("section")
        section: Section,
        @Query("mature")
        mature: Boolean? = true,
        @Query("album_previews")
        albumPreviews: Boolean? = true
    ): Response<GalleryResponse>

    @GET("tags")
    suspend fun getTags(): Response<TagsResponse>

    @GET("gallery/t/{tag}")
    suspend fun getTagGallery(
        @Path("tag")
        tag : String
    ) : Response<TagResponse>

    @GET("gallery/{albumHash}")
    suspend fun getGalleryImage(
        @Path("albumHash")
        albumHash: String
    ) : Response<GalleryImageResponse>

    @GET("gallery/{albumHash}/comments")
    suspend fun getComments (
        @Path("albumHash")
        albumHash:  String
    ) : Response<CommentsResponse>
}