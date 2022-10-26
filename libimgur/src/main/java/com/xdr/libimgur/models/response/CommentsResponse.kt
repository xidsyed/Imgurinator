package com.xdr.libimgur.models.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.xdr.libimgur.models.Comment

@JsonClass(generateAdapter = true)
data class CommentsResponse(
    @Json(name = "data")
    val `data`: List<Comment>?,
    @Json(name = "status")
    val status: Int?,
    @Json(name = "success")
    val success: Boolean?
)