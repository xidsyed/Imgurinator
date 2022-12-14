package com.xdr.imgurinator.util

sealed class State<out T : Any> {
    object Fetching : State<Nothing>()
    class Fetched<out T : Any>(val data: T) : State<T>()
    class Error(val message: String?, val exception: Exception?) : State<Nothing>()
}