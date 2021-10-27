package com.chao.search.repository

import androidx.lifecycle.LiveData
import com.chao.search.api.model.ImageResponse
import com.chao.search.api.ImageService
import com.chao.search.ui.state.MainViewState
import com.chao.search.util.ApiSuccessResponse
import com.chao.search.util.DataState
import com.chao.search.util.GenericApiResponse
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepositoryImpl
@Inject
constructor(val imageService: ImageService) : MainRepository {

    var mainViewState = MainViewState()

    override fun getImages(query: String): LiveData<DataState<MainViewState>> {
        return  object : NetworkBoundResource<ImageResponse, MainViewState>() {
            override fun createCall(): LiveData<GenericApiResponse<ImageResponse>> {
               return imageService.searchImages(query)
            }

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<ImageResponse>) {
                mainViewState = mainViewState.copy(images = response.body.photos.photo)
                result.value = DataState.data(data = mainViewState)
            }
        }.asLiveData()
    }
}
