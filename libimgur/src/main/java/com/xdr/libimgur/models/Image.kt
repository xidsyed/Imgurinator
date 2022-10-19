package com.xdr.libimgur.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "datetime")
    val datetime: Int,
    @Json(name = "cover")
    val cover: String?,
    @Json(name = "cover_width")
    val coverWidth: Int?,
    @Json(name = "cover_height")
    val coverHeight: Int?,
    @Json(name = "account_url")
    val accountUrl: String,
    @Json(name = "account_id")
    val accountId: Int,
    @Json(name = "privacy")
    val privacy: String?,
    @Json(name = "layout")
    val layout: String?,
    @Json(name = "views")
    val views: Int,
    @Json(name = "link")
    val link: String,
    @Json(name = "ups")
    val ups: Int,
    @Json(name = "downs")
    val downs: Int,
    @Json(name = "points")
    val points: Int,
    @Json(name = "score")
    val score: Int,
    @Json(name = "is_album")
    val isAlbum: Boolean,
    @Json(name = "vote")
    val vote: Any?,
//    @Json(name = "favorite")
//    val favorite: Boolean?,
//    @Json(name = "nsfw")
//    val nsfw: Boolean,
    @Json(name = "section")
    val section: String,
    @Json(name = "comment_count")
    val commentCount: Int,
    @Json(name = "favorite_count")
    val favoriteCount: Int,
    @Json(name = "topic")
    val topic: Any?,
    @Json(name = "topic_id")
    val topicId: Int?,
    @Json(name = "images_count")
    val imagesCount: Int?,
    @Json(name = "in_gallery")
    val inGallery: Boolean,
    @Json(name = "is_ad")
    val isAd: Boolean,
    @Json(name = "tags")
    val tags: List<Tag>,
    @Json(name = "ad_type")
    val adType: Int,
    @Json(name = "ad_url")
    val adUrl: String,
    @Json(name = "in_most_viral")
    val inMostViral: Boolean,
    @Json(name = "include_album_ads")
    val includeAlbumAds: Boolean?,
//    @Json(name = "ad_config")
//    val adConfig: AdConfig,
    @Json(name = "type")
    val type: String?,
    @Json(name = "animated")
    val animated: Boolean?,
    @Json(name = "width")
    val width: Int?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "size")
    val size: Int?,
    @Json(name = "bandwidth")
    val bandwidth: Long?,
    @Json(name = "has_sound")
    val hasSound: Boolean?,
    @Json(name = "edited")
    val edited: Int?,
    @Json(name = "mp4")
    val mp4: String?,
    @Json(name = "gifv")
    val gifv: String?,
    @Json(name = "hls")
    val hls: String?,
    @Json(name = "mp4_size")
    val mp4Size: Int?,
    @Json(name = "looping")
    val looping: Boolean?,
    @Json(name = "processing")
    val processing: Processing?
) {
    @JsonClass(generateAdapter = true)
    data class AdConfig(
        @Json(name = "safeFlags")
        val safeFlags: List<String>,
        @Json(name = "highRiskFlags")
        val highRiskFlags: List<Any>,
        @Json(name = "unsafeFlags")
        val unsafeFlags: List<String>,
        @Json(name = "wallUnsafeFlags")
        val wallUnsafeFlags: List<String>,
        @Json(name = "showsAds")
        val showsAds: Boolean,
        @Json(name = "showAdLevel")
        val showAdLevel: Int
    )
}