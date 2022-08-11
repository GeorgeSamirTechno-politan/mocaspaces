package com.technopolitan.mocaspaces.modules

import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class OkHttpClient {

    @Provides
    @Singleton
    @Named("withoutAuth")
    fun httpClientWithoutAuth(): OkHttpClient {
        val requestTimeOut = 90
        val builder = OkHttpClient.Builder()
            .readTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return builder.build()
    }


    @Provides
    @Singleton
    @Named("withAuth")
    fun httpClientWithAuth(): OkHttpClient {
        val requestTimeOut = 90
        val builder = OkHttpClient.Builder()
            .readTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return builder.build()
    }

}