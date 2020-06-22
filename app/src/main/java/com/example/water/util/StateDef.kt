package com.example.water.util

sealed class State<T>(val value: T?) {

//    class None<T>: State<T>()

    data class InProgress<T>(val prevValue: T?) : State<T>(null)

    data class Success<T>(val data: T?) : State<T>(data)

    class Error<T>(val error: Throwable? = null, data: T? = null) : State<T>(data)
}