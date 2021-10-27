package com.chao.search.ui.fragment

import androidx.lifecycle.*
import com.chao.search.api.model.ImageResult
import com.chao.search.repository.MainRepository
import com.chao.search.ui.state.MainStateEvent
import com.chao.search.ui.state.MainViewState
import com.chao.search.util.AbsentLiveData
import com.chao.search.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val mainRepositoryImpl: MainRepository
): ViewModel() {


    private val _viewStat: MutableLiveData<MainViewState> = MutableLiveData()
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()


    val viewState: LiveData<MainViewState>
    get() = _viewStat

    val dataState:  LiveData<DataState<MainViewState>> = Transformations.switchMap(_stateEvent) { stateEvent ->
        stateEvent?.let {
            handleStateEvent(stateEvent)
        }
    }

    private fun handleStateEvent(stateEvent: MainStateEvent):  LiveData<DataState<MainViewState>> = when(stateEvent) {
            is MainStateEvent.SearchImages -> {
               mainRepositoryImpl.getImages(stateEvent.query)
            }
            is MainStateEvent.None -> {
                AbsentLiveData.create()
            }
    }


    fun setImages(images: List<ImageResult>) {
        val update = getCurrentViewStateorNew()
        update.images = images
        _viewStat.value = update
    }

    private fun getCurrentViewStateorNew(): MainViewState {
        val value = viewState.value?.let {
            it
        } ?: MainViewState()

        return value
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}
