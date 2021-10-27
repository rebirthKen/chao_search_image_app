package com.chao.search.repository

import androidx.lifecycle.LiveData
import com.chao.search.ui.state.MainViewState
import com.chao.search.util.DataState

interface MainRepository {
    fun getImages(query: String): LiveData<DataState<MainViewState>>

}