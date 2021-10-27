package com.chao.search.ui.state

import com.chao.search.api.model.ImageResult


data class MainViewState(
    var images: List<ImageResult> = emptyList(),
    var similarImages: List<ImageResult> = emptyList()
)