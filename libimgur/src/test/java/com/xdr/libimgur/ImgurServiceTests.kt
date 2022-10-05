package com.xdr.libimgur

import com.xdr.libimgur.apis.ImgurService
import junit.framework.Assert.assertNotNull
import okhttp3.OkHttpClient
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ImgurServiceTests {

    private val client = OkHttpClient.Builder()
        .addInterceptor{
            val request =  it.request().newBuilder()
                .addHeader("Authorization", "Client-ID b994fc7c4ed91f1")
                .build()
            it.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgur.com/3/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build();                                                       // create retrofit instance
    private val service = retrofit.create(ImgurService::class.java)     // create an implementation of API Endpoints


    @Test
    fun `get tags working` () {
        val response = service.getTags().execute()
        assertNotNull(response.body())
    }

    @Test
    fun `get galleries working` () {
        val response = service.getGallery().execute()
        assertNotNull(response.body())
    }
}