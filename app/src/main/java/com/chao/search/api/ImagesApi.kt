package com.chao.search.api

import androidx.lifecycle.LiveData
import com.chao.search.api.model.ImageResponse
import com.chao.search.util.GenericApiResponse

import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=1508443e49213ff84d566777dc211f2a")
     fun searchImages(@Query(value = "text") searchTerm: String): LiveData<GenericApiResponse<ImageResponse>>

}
