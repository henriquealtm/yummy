package com.example.network

sealed class Resource<out T>(val data: T? = null) {

    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(val errorData: NetworkError) : Resource<T>()
    class Loading<T> : Resource<T>()

    fun <R> resourceType(onSuccess: (T?) -> R?): Resource<R> = when (this) {
        is Success -> Success(
            onSuccess(
                data
            )
        )
        is Error -> Error(errorData)
        is Loading -> Loading()
    }

}
