package com.technopolitan.mocaspaces.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.technopolitan.mocaspaces.enums.SharedPrefKey
import com.technopolitan.mocaspaces.utilities.Constants
import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Module
class OkHttpClient @Inject constructor(
    private var sharedPrefModule: SharedPrefModule,
    private var context: Context
) {

    @Provides
    @Singleton
    @Named("withoutAuth")
    fun httpClientWithoutAuth(): OkHttpClient {
        val requestTimeOut = 90
        val builder = OkHttpClient.Builder()
            .readTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .connectTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(requestTimeOut.toLong(), TimeUnit.SECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        if (!Constants.IsAppLive)
            builder.addInterceptor(ChuckerInterceptor(context))
        builder.addNetworkInterceptor(httpLoggingInterceptor)
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
        if (!Constants.IsAppLive)
            builder.addInterceptor(ChuckerInterceptor(context))
        builder.addNetworkInterceptor(httpLoggingInterceptor)
        builder.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .addHeader(
                    "Authorization",
                    "Bearer ${sharedPrefModule.getStringFromShared(SharedPrefKey.BearerToken.name)}"
                )
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return builder.build()
    }

}