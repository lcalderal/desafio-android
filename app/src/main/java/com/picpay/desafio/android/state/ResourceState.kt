package com.picpay.desafio.android.state

sealed class ResourceState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ResourceState<T>(data)
    class Error<T>(data: T?, message: String): ResourceState<T>(data, message)
    class Loading<T>: ResourceState<T>()
}
