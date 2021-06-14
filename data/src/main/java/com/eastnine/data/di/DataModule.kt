package com.eastnine.data.di

import android.content.Context
import androidx.room.Room
import com.eastnine.data.api.service.GCService
import com.eastnine.data.db.CoreDataBase
import com.eastnine.data.db.dao.TravelDao
import com.eastnine.data.repository.GCRepositoryImpl
import com.eastnine.domain.repository.GCRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideGCService(retrofitBuilder: Retrofit): GCService =
        retrofitBuilder.create(GCService::class.java)

    @Singleton
    @Provides
    fun provideCoreDataBase(@ApplicationContext context: Context): CoreDataBase =
        Room.databaseBuilder(context, CoreDataBase::class.java, CoreDataBase.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideTravelDao(coreDataBase: CoreDataBase): TravelDao =
        coreDataBase.travelDao()

    @Singleton
    @Provides
    fun provideGCRepository(travelDao: TravelDao, service: GCService): GCRepository =
        GCRepositoryImpl(travelDao, service)
}