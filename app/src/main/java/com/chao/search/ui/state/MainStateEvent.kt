package com.chao.search.ui.state

sealed class MainStateEvent {
    data class SearchImages(val query: String): MainStateEvent()

    object None: MainStateEvent()
}