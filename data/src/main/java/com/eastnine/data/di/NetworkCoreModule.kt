package com.eastnine.data.di

import com.eastnine.data.BuildConfig
import com.eastnine.data.factory.NullOnEmptyConverterFactory
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkCoreModule {
    init {
        System.loadLibrary("native-lib")
    }

    external fun baseUrl(): String

    @Singleton
    @Provides
    fun provideNullOnEmptyConverterFactory(): NullOnEmptyConverterFactory =
        NullOnEmptyConverterFactory.create()

    @Singleton
    @Provides
    fun provideCallAdapter(): RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io())

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())

        if(BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(interceptor)
        }

        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        nullOnEmptyConverterFactory: NullOnEmptyConverterFactory,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory,
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl())
        .addCallAdapterFactory(rxJava3CallAdapterFactory)
        .addConverterFactory(nullOnEmptyConverterFactory)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}