package com.example.firebasenewsapplesson.data.state

import com.example.firebasenewsapplesson.data.model.News

sealed class NewsListState{

    object Idle : NewsListState()
    object Loading : NewsListState()
    class Result(val news: List<News>) : NewsListState()
    class Error(val throwable: Throwable? = null) : NewsListState()


}
