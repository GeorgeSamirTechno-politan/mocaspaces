package com.technopolitan.mocaspaces.data

import android.util.Log

open class ApiStatus<T>(var message: String?, var data: T?)
class LoadingStatus<T> : ApiStatus<T>(null, null)
class FailedStatus<T>(message: String?) : ApiStatus<T>(message, null) {
    init {
        Log.e(this.javaClass.name, "ErrorState() \t message: $message")
    }
}

class ErrorStatus<T>(message: String?) : ApiStatus<T>(message, null) {
    init {
        Log.e(this.javaClass.name, "ErrorState() \t message: $message")
    }
}

class SuccessStatus<T>(message: String?, data: T?) : ApiStatus<T>(message, data)
class ProgressStatus<T>(message: String?, data: T?) : ApiStatus<T>(message, data)