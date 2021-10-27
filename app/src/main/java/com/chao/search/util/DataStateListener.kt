package com.chao.search.util


interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}