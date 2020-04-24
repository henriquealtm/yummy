package com.example.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val EXPIRED_SESSION_CODE = 401

fun <RESPONSE> makeApiCall(
    call: Call<RESPONSE>
): LiveData<Resource<RESPONSE>> {
    val result = MutableLiveData<Resource<RESPONSE>>()

    result.postValue(Resource.Loading())

    call.enqueue(object : Callback<RESPONSE> {
        override fun onFailure(call: Call<RESPONSE>, t: Throwable) {
            val networkError = when (t) {
                is ConnectException, is UnknownHostException -> NetworkError.ConnectionError
                is SocketTimeoutException -> NetworkError.TimeOutError
                else -> NetworkError.FailureError
            }

            result.postValue(Resource.Error(networkError))
        }

        override fun onResponse(call: Call<RESPONSE>, response: Response<RESPONSE>) {
            if (response.isSuccessful) {
                result.postValue(Resource.Success(response.body()))
            } else {
                httpNetworkError(response)
            }
        }
    })

    return result
}

private fun <RESPONSE> httpNetworkError(response: Response<RESPONSE>): NetworkError {
    if (response.code() == EXPIRED_SESSION_CODE) {
        return NetworkError.ExpiredSession
    }

    return try {
        val apiError =
            Gson().fromJson(response.errorBody()?.charStream(), ResponseBodyError::class.java)

        NetworkError.ResponseError(
            response.code(),
            apiError.error
        )
    } catch (exception: Exception) {
        NetworkError.ResponseError(response.code())
    }
}