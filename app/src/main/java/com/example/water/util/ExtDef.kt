package com.example.water.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import retrofit2.Response

fun <T> Fragment.observe(data: LiveData<T>, block: T.() -> Unit) =
    data.observe(viewLifecycleOwner, Observer(block))

fun <T> Response<T>.parseState(): State<T> =
    when {
        isSuccessful -> State.Success(body())
        else -> State.Error(errorBody()?.string()?.let { Throwable(it) })
    }

fun <T> State<T>?.inProgress() = State.InProgress<T>(this?.value)

fun <T> State<T>.doOnSuccess(block: T?.() -> Unit): State<T> {
    when (this) {
        is State.Success -> block(data)
    }
    return this
}

fun <T> State<T>.doOnError(block: Throwable?.() -> Unit): State<T> {
    when (this) {
        is State.Error -> block(error)
    }
    return this
}