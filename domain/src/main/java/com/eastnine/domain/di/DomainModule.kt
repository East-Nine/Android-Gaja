package com.eastnine.domain.di

import com.eastnine.domain.repository.GCRepository
import com.eastnine.domain.usecase.GCUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGCUseCase(repository: GCRepository): GCUseCase = GCUseCase(repository)
}