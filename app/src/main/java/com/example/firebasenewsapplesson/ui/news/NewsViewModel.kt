package com.example.firebasenewsapplesson.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasenewsapplesson.data.repository.NewsRepository
import com.example.firebasenewsapplesson.data.state.NewsListState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {


    private val _newsListState: MutableStateFlow<NewsListState> =
        MutableStateFlow(NewsListState.Idle)
    val newsListState: StateFlow<NewsListState> = _newsListState


    fun getNews(editorId: String? = null) {
        viewModelScope.launch {
            kotlin.runCatching {
                _newsListState.value = NewsListState.Loading
                newsRepository.getNews(editorId).also {
                    _newsListState.value = NewsListState.Result(it.map { NewsAdapterModel(it,firebaseAuth.currentUser?.uid.orEmpty()) })
                }
            }.onFailure {
                _newsListState.value = NewsListState.Error(it)

            }
        }
    }


}