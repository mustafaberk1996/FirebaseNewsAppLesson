package com.example.firebasenewsapplesson.data.state

sealed class AddNewsState {
    object Idle:AddNewsState()
    object Loading:AddNewsState()
    object Success:AddNewsState()
    class Error(val throwable: Throwable?= null):AddNewsState()

}