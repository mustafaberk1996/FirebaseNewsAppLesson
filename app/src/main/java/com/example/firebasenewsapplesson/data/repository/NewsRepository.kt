package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.data.model.News

interface NewsRepository {
    suspend fun addNews(news: News)

    suspend fun listenNews()
    suspend fun getNews(editorId: String?):List<News>
}