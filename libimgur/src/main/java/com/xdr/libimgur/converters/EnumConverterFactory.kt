package com.xdr.libimgur.converters

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.reflect.Type
import java.util.*

class EnumConverterFactory : Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<Enum<*>, String>? {    // TODO : Why cant i just '=' this whole thing
        return if (type is Class<*> && type.isEnum) {
            Converter<Enum<*>, String>() { enum ->   // TODO : How is an interface accepting a lambda
                try {
                    enum.toString().lowercase()
                } catch (e: Exception) {
                    null
                }
            }
        } else null
    }
}