package com.example.network

import java.io.Serializable

sealed class NetworkError : Serializable {

    object ConnectionError : NetworkError()

    object ExpiredSession : NetworkError()

    object TimeOutError : NetworkError()

    object FailureError : NetworkError()

    class ResponseError(
        val code: Int,
        val error: String? = null
    ) : NetworkError()

}