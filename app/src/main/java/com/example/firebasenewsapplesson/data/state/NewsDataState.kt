package com.example.firebasenewsapplesson.data.state

sealed class NewsDataState {
    object Idle:NewsDataState()
    object Added:NewsDataState()
    object Removed:NewsDataState()
    object Modified:NewsDataState()

}