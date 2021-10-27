package com.chao.search.api.model

import com.chao.search.util.StableIdItem

data class ImageResult(
        override val id: String,
        val owner: String,
        val secret: String,
        val server: String,
        val farm: Int,
        val title: String): StableIdItem
