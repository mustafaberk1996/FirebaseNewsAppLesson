package com.example.firebasenewsapplesson.data.di

import com.example.firebasenewsapplesson.data.state.NewsDataState
import com.example.firebasenewsapplesson.data.state.ProfilePhotoUpdateState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StateModule {



    @Singleton
    @Provides
    fun provideNewsState():NewsDataState = NewsDataState.Idle

    @Singleton
    @Provides
    fun provideNewsMutableState(newsDataState: NewsDataState = NewsDataState.Idle):MutableStateFlow<NewsDataState> = MutableStateFlow(newsDataState)

    @Singleton
    @Provides
    fun provideProfilePhotoUpdateState():ProfilePhotoUpdateState = ProfilePhotoUpdateState.Idle

    @Singleton
    @Provides
    fun provideMutableProfilePhotoUpdateState(profilePhotoUpdateState: ProfilePhotoUpdateState)
    :MutableSharedFlow<ProfilePhotoUpdateState> = MutableSharedFlow()


}