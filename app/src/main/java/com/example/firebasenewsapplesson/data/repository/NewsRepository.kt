package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.data.model.News

interface NewsRepository {
    suspend fun addNews(news: News)
}