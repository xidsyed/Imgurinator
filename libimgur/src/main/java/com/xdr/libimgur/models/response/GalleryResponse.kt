package com.xdr.libimgur.models.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.xdr.libimgur.models.Image

@JsonClass(generateAdapter = true)
data class GalleryResponse(
    @Json(name = "data")
    val `data`: List<Image>,
    @Json(name = "success")
    val success: Boolean,
    @Json(name = "status")
    val status: Int
)