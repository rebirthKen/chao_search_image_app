package com.chao.search.api

import androidx.lifecycle.LiveData
import com.chao.search.api.model.ImageResponse
import com.chao.search.util.GenericApiResponse
import javax.inject.Inject

class ImageServiceImpl @Inject constructor(val api: ImagesApi): ImageService {
    override fun searchImages(phrase: String): LiveData<GenericApiResponse<ImageResponse>> {
        return api.searchImages(phrase)
    }
}
