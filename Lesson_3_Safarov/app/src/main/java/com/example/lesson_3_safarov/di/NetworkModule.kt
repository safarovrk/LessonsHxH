package com.example.lesson_3_safarov.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.lesson_3_safarov.data.ApiLesson
import com.example.lesson_3_safarov.data.interceptors.AuthorizationInterceptor
import com.example.lesson_3_safarov.data.repository.PreferenceStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    companion object {
        private const val BASE_ENDPOINT = "http://45.144.64.179/cowboys/api/"
    }

    @Provides
    fun provideOkHttp(
        context: Context,
        preferenceStorage: PreferenceStorage,
    ) = OkHttpClient.Builder().apply {
        addInterceptor(AuthorizationInterceptor(preferenceStorage))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(loggingInterceptor)
        addInterceptor(ChuckerInterceptor(context))
    }
        .connectTimeout(20000L, TimeUnit.MILLISECONDS)
        .readTimeout(20000L, TimeUnit.MILLISECONDS)
        .writeTimeout(20000L, TimeUnit.MILLISECONDS)
        .build()

    @Provides
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson,
    ) = Retrofit.Builder()
        .baseUrl(BASE_ENDPOINT)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .build()

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    fun provideApiService(
        retrofit: Retrofit,
    ): ApiLesson {
        return retrofit.create(ApiLesson::class.java)
    }
}