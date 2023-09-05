package com.example.firebasenewsapplesson.data.repository

import android.net.Uri
import com.example.firebasenewsapplesson.data.model.News

interface NewsRepository {
    suspend fun addNews(news: News, uris: List<Uri>)

    suspend fun listenNews()
    suspend fun getNews(editorId: String?):List<News>
}