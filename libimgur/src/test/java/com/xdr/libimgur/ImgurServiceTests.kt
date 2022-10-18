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

        val response = imgurService.getGallery(Section.HOT)
        assertNotNull(response)
    }

    @Test
    fun `get top galleries working`() = runBlocking{
        val response = imgurService.getGallery(Section.TOP)
        assertNotNull(response.body())
    }

}