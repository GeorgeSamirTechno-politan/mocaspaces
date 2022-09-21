package com.technopolitan.mocaspaces.bases

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

abstract class BaseRemote<T,E> {

    private val apiMediator : MediatorLiveData<ApiStatus<T>> = MediatorLiveData()

    fun handleApi() : MediatorLiveData<ApiStatus<T>>{
        apiMediator.value = LoadingStatus()
        val source : LiveData<ApiStatus<T>> = LiveDataReactiveStreams.fromPublisher(
            flowable().map { handleResponse(it) }
                .doOnError{
                    it?.printStackTrace()
                }
                .onErrorReturn { error ->
                    handlerError(error as HttpException)
                }.subscribeOn(Schedulers.io())
        )
        apiMediator.addSource(source) {
            apiMediator.value = it
            apiMediator.removeSource(source)
        }
        return apiMediator
    }

    fun getSource(): LiveData<ApiStatus<T>> = apiMediator

    abstract fun flowable(): Flowable<HeaderResponse<E>>

    abstract fun handleResponse(it: HeaderResponse<E>): ApiStatus<T>

    private fun handlerError(it: HttpException): ApiStatus<T> {
        return if (it.code() == 404)
            ErrorStatus("Failed to connect server")
        else {
            try {
               val message = JsonParser().parse(it.response()!!.errorBody()!!.string()).asJsonObject.get("Message").asString
               ErrorStatus(message)
           }catch (e: Exception){
               ErrorStatus("Failed to connect server with code:$${it.code()}")
           }
        }
    }
}