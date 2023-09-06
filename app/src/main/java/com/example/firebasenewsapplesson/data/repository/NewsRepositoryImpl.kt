package com.example.firebasenewsapplesson.data.repository

import android.net.Uri
import com.example.firebasenewsapplesson.Constants.EDITOR_ID
import com.example.firebasenewsapplesson.Constants.NEWS
import com.example.firebasenewsapplesson.data.model.News
import com.example.firebasenewsapplesson.data.state.NewsDataState
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mutableNewsDataState: MutableStateFlow<NewsDataState>,
    private val firebaseStorage: FirebaseStorage
):NewsRepository {
    override suspend fun addNews(news: News, uris: List<Uri>) {
        println("addNews")
        firestore.collection(NEWS).add(news).addOnSuccessListener {
            it.update(mapOf("id" to it.id ))
            CoroutineScope(Dispatchers.IO).launch {
                uploadNewsPhoto(it.id, uris)
            }
        }
    }


    private suspend fun uploadNewsPhoto(newsId: String, uris: List<Uri>) {

        println("uploadNewsPhoto")
        uris.forEach {

            val photoId = UUID.randomUUID().toString()

            firebaseStorage.reference.child(photoId).putFile(it).addOnCompleteListener {
                if (it.isSuccessful){
                    CoroutineScope(Dispatchers.IO).launch {
                        val downloadUrl = it.result.storage.downloadUrl.await()
                        firestore.collection(NEWS).document(newsId).update("photos",FieldValue.arrayUnion(downloadUrl))
                        println("GotDownloadUrl")
                    }
                }

            }




        }


    }
    override suspend fun listenNews() {
        firestore.collection(NEWS).addSnapshotListener { value, error ->
            if (error!=null) return@addSnapshotListener

            value?.documentChanges?.forEach {
                val news = it.document.toObject(News::class.java)
                println("news: $news, ${it.type}")
                when (it.type) {
                    DocumentChange.Type.ADDED -> {
                        mutableNewsDataState.value = NewsDataState.Added
                    }
                    DocumentChange.Type.MODIFIED -> {
                        mutableNewsDataState.value = NewsDataState.Modified
                    }
                    DocumentChange.Type.REMOVED -> {
                        mutableNewsDataState.value = NewsDataState.Removed
                    }
                }
            }
        }
    }

    override suspend fun getNews(editorId: String?): List<News> {
        return editorId?.let {
            firestore.collection(NEWS).whereEqualTo(EDITOR_ID, it).get().await().toObjects(News::class.java)
        } ?: kotlin.run {
            firestore.collection(NEWS).get().await().toObjects(News::class.java)
        }
    }
}