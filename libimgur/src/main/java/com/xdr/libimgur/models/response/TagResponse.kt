package com.xdr.libimgur.models.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.xdr.libimgur.models.Tag

@JsonClass(generateAdapter = true)
data class TagResponse(
    @Json(name = "data")
    val `data`: Tag?,
    @Json(name = "status")
    val status: Int?,
    @Json(name = "success")
    val success: Boolean?
)