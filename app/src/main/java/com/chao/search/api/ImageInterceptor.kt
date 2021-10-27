package com.chao.search.api

import com.chao.search.App
import com.chao.search.R
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ImageInterceptor @Inject constructor() : Interceptor {
    @Inject
    lateinit var app: App
    
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val apiKey = app.applicationContext.getString(R.string.api_key)
        val request = chain.request()
                .newBuilder()
                .header("Api-Key", apiKey)
                .build()
        
        return chain.proceed(request)
    }
}
