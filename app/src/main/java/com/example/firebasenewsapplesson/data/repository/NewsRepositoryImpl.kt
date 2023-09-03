package com.example.firebasenewsapplesson.data.repository

import com.example.firebasenewsapplesson.Constants.NEWS
import com.example.firebasenewsapplesson.data.model.News
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):NewsRepository {
    override suspend fun addNews(news: News) {
        firestore.collection(NEWS).add(news)
            .addOnSuccessListener {
            it.update(mapOf("id" to it.id ))
        }
    }
}