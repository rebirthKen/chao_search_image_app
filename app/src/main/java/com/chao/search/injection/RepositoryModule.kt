package com.chao.search.injection

import com.chao.search.api.ImageServiceImpl
import com.chao.search.api.ImagesApi
import com.chao.search.api.ImageService
import com.chao.search.repository.MainRepository
import com.chao.search.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun provideImageService(imagesApi: ImagesApi): ImageService {
        return ImageServiceImpl(imagesApi)
    }


    @Singleton
    @Provides
    fun provideMainRepository(imageService: ImageService): MainRepository {
        return MainRepositoryImpl(imageService)
    }
}















