package com.technopolitan.mocaspaces.bases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.google.gson.JsonParser
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.ErrorStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.LoadingStatus
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

abstract class BaseRemote<T, E> {

    protected var apiMediator: MediatorLiveData<ApiStatus<T>> = MediatorLiveData()

    fun handleApi(): MediatorLiveData<ApiStatus<T>> {
        apiMediator.value = LoadingStatus()
        val source: LiveData<ApiStatus<T>> = LiveDataReactiveStreams.fromPublisher(
            flowable().map { handleResponse(it) }
                .doOnError {
                    it?.printStackTrace()
                }
                .onErrorReturn { error ->
                    handlerError(error)
                }.subscribeOn(Schedulers.io())
        )
        apiMediator.addSource(source) {
            apiMediator.value = it
            apiMediator.removeSource(source)
        }
        return apiMediator
    }

    fun getRemaining(pageTotal: Int, pageSize: Int, pageNumber: Int): Int {
        var remaining: Int = 0
        if (pageTotal > pageSize) {
            remaining = pageTotal / pageNumber
        }
        return remaining
    }

    fun getSource(): LiveData<ApiStatus<T>> = apiMediator

    abstract fun flowable(): Flowable<HeaderResponse<E>>

    abstract fun handleResponse(it: HeaderResponse<E>): ApiStatus<T>

    private fun handlerError(it: Throwable): ApiStatus<T> {
        return try {
            val error = (it as HttpException)
            if (error.code() == 404)
                ErrorStatus("Failed to connect server")
            else {
                val message = JsonParser().parse(
                    error.response()!!.errorBody()!!.string()
                ).asJsonObject.get("Message").asString
                ErrorStatus(message)
            }
        } catch (e: Exception) {
            Log.e(javaClass.name, "handlerError: ", it)
            ErrorStatus("Failed to connect server")
        }
    }
}