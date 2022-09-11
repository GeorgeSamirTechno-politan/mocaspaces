package com.technopolitan.mocaspaces.bases

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import com.technopolitan.mocaspaces.data.ApiStatus
import com.technopolitan.mocaspaces.data.ErrorStatus
import com.technopolitan.mocaspaces.data.HeaderResponse
import com.technopolitan.mocaspaces.data.LoadingStatus
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

abstract class BaseRemote<T,R,E> {

    private val apiMediator : MediatorLiveData<ApiStatus<T>> = MediatorLiveData()

    fun handleApi(request: R) : MediatorLiveData<ApiStatus<T>>{
        apiMediator.value = LoadingStatus()
        val source : LiveData<ApiStatus<T>> = LiveDataReactiveStreams.fromPublisher(
            flowable().map { handleResponse(it) }
                .doOnError{
                    it?.printStackTrace()
                }
                .onErrorReturn() { error ->
                    error.message?.let { handlerError(it)    }
                } .subscribeOn(Schedulers.io())
        )
        apiMediator.addSource(source) {
            apiMediator.value = it
            apiMediator.removeSource(source)
        }
        return apiMediator
    }

    abstract fun flowable():Flowable<HeaderResponse<E>>

    abstract fun handleResponse(it: HeaderResponse<E>): ApiStatus<T>

    private fun handlerError(it: String): ApiStatus<T> = ErrorStatus(it)
}