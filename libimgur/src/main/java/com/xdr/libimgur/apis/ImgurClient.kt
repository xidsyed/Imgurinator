package com.xdr.libimgur.apis

import com.xdr.libimgur.converters.EnumConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object ImgurClient {
    private const val API_KEY = "b994fc7c4ed91f1"   // TODO: Store API KEY securely

    // Build httpLoggingInterceptor
    private val logger : HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    // Build okHttpClient instance to add auth headers
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor {
                val authRequest = it.request().newBuilder()
                    .addHeader("Authorization", "Client-ID $API_KEY")
                    .build()
                it.proceed(authRequest)
            }
            .addInterceptor(logger)
            .build()
    }

    // Build retrofitInstance
    private val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .baseUrl("https://api.imgur.com/3/")
            .build()
    }

    // create an implementation of API Endpoints
    val imgurService: ImgurService by lazy { retrofitInstance.create(ImgurService::class.java) }
}