package com.example.firebasenewsapplesson.data.state

import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.ui.news.NewsAdapterModel

sealed class NewsListState{

    object Idle : NewsListState()
    object Loading : NewsListState()
    class Result(val newsAdapterModel: List<NewsAdapterModel>) : NewsListState()
    class Error(val throwable: Throwable? = null) : NewsListState()


}
