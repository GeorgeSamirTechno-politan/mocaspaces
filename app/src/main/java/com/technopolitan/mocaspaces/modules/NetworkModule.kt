package com.technopolitan.mocaspaces.modules


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.technopolitan.mocaspaces.network.BaseUrl
import com.technopolitan.mocaspaces.network.ServiceInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton


@Module
class NetworkModule @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private var context: Context
) {


    @Singleton
    @Provides
    fun provideServiceInterfaceWithoutAuth(serviceName: String): ServiceInterface {
        return Retrofit.Builder().baseUrl(BaseUrl.baseUrl(serviceName))
            .client(okHttpClient.httpClientWithoutAuth())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideServiceInterfaceWithAuth(serviceName: String): ServiceInterface {
        return Retrofit.Builder().baseUrl(BaseUrl.baseUrl(serviceName))
            .client(okHttpClient.httpClientWithAuth())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ServiceInterface::class.java)
    }

    @Suppress("DEPRECATION")
    fun isInternetAvailable(): Boolean {
        val result: Boolean
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }

        return result
    }

}