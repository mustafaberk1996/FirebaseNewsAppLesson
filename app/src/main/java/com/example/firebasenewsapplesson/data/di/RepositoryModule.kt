package com.example.firebasenewsapplesson.data.di

import com.example.firebasenewsapplesson.data.repository.AuthRepository
import com.example.firebasenewsapplesson.data.repository.AuthRepositoryImpl
import com.example.firebasenewsapplesson.data.repository.NewsRepository
import com.example.firebasenewsapplesson.data.repository.NewsRepositoryImpl
import com.example.firebasenewsapplesson.data.repository.UserRepository
import com.example.firebasenewsapplesson.data.repository.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl):AuthRepository = authRepositoryImpl

    @Provides
    @Singleton
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl):UserRepository = userRepositoryImpl

    @Provides
    @Singleton
    fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl):NewsRepository = newsRepositoryImpl
}