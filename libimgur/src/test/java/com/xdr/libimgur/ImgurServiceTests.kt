package com.xdr.libimgur

import com.xdr.libimgur.apis.ImgurClient
import com.xdr.libimgur.params.Section
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ImgurServiceTests {

    private val imgurService = ImgurClient.imgurService

    @Test
    fun `get tags working`()  = runBlocking {
        val response = imgurService.getTags()
        assertNotNull(response.body())
    }

    @Test
    fun `get hot galleries working`()  = runBlocking{

        val response = imgurService.getGallerySection(Section.HOT)
        assertNotNull(response)
    }

    @Test
    fun `get top galleries working`() = runBlocking{
        val response = imgurService.getGallerySection(Section.TOP)
        assertNotNull(response.body())
    }

    @Test
    fun `get tag aww working`() = runBlocking{
        val response = imgurService.getTagGallery("awww")
        assertNotNull(response.body())
    }

    @Test
    fun `get comments working`() = runBlocking{
        val response = imgurService.getComments("lzvHpeU")
        assertNotNull(response.body())
    }

    @Test
    fun `get gallery image working`() = runBlocking{
        val response = imgurService.getGalleryImage("lzvHpeU")
        assertNotNull(response.body())
    }


}