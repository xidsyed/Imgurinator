package com.xdr.imgurinator.util

import java.util.*

object Util {
    public fun getTimeDiff(oldTime: Long): String {
        val diffInMillis : Long = Date().time - oldTime
        val seconds = diffInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = months / 12

        if(years > 0) return "$years y"
        if(months > 0) return "$months m"
        if (days > 0) return "$days d"
        if (hours > 0) return "$hours h"
        if (minutes > 0 ) return "$minutes min"
        return "$seconds s"
    }
}