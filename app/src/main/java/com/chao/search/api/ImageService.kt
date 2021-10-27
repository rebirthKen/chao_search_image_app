package com.chao.search.api

import androidx.lifecycle.LiveData
import com.chao.search.api.model.ImageResponse
import com.chao.search.util.GenericApiResponse

interface ImageService {
    fun searchImages(phrase: String): LiveData<GenericApiResponse<ImageResponse>>
}