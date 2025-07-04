package com.example.common.functions

import androidx.paging.PagingSource.LoadResult
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkException(
    val error: NetworkError,
    val label: String
): Exception()

fun processNetworkErrors(statusCode: Int): NetworkError {
    return when (statusCode) {
        in 200..299 -> NetworkErrors.SUCCESS
        401 -> NetworkErrors.UNAUTHORIZED
        408 -> NetworkErrors.REQUEST_TIMEOUT
        409 -> NetworkErrors.CONFLICT
        413 -> NetworkErrors.PAYLOAD_TOO_LARGE
        429 -> NetworkErrors.TOO_MANY_REQUESTS
        in 500..599 -> NetworkErrors.SERVER_ERROR
        else -> NetworkErrors.UNKNOWN
    }
}

fun processNetworkErrorsForUi(error: NetworkError): String {
    return when (error) {
        NetworkErrors.SUCCESS -> "All is successful"
        NetworkErrors.UNAUTHORIZED -> "Seems you are an unauthorized person"
        NetworkErrors.REQUEST_TIMEOUT -> "Timeout, please retry"
        NetworkErrors.CONFLICT -> "Seems something conflict"
        NetworkErrors.PAYLOAD_TOO_LARGE -> "Payload too large"
        NetworkErrors.TOO_MANY_REQUESTS -> "Too many requests, give AniLibria a little rest :)"
        NetworkErrors.SERVER_ERROR -> "Server error"
        NetworkErrors.INTERNET -> "Please connect to internet :)"
        NetworkErrors.SERIALIZATION -> "Hmmm... Problem with serialization"
        else -> "Mystic and unknown error"
    }
}

fun <K : Any, V : Any> processNetworkExceptionsForPaging(e: Throwable): LoadResult<K, V> {
    return when (e) {
        is UnknownHostException, is SocketException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            LoadResult.Error(NetworkException(NetworkErrors.INTERNET, label))
        }
        is SocketTimeoutException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.REQUEST_TIMEOUT)
            LoadResult.Error(NetworkException(NetworkErrors.REQUEST_TIMEOUT, label))
        }
        else -> {
            val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
            LoadResult.Error(NetworkException(NetworkErrors.UNKNOWN, label))
        }
    }
}

fun processNetworkExceptions(e: Exception): NetworkResponse {
    return when (e) {
        is UnknownHostException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            NetworkResponse(
                error = NetworkErrors.INTERNET,
                label = label
            )
        }
        is SocketException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            NetworkResponse(
                error = NetworkErrors.INTERNET,
                label = label
            )
        }
        is SocketTimeoutException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.REQUEST_TIMEOUT)
            NetworkResponse(
                error = NetworkErrors.REQUEST_TIMEOUT,
                label = label
            )
        }
        else -> {
            val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
            NetworkResponse(
                error = NetworkErrors.UNKNOWN,
                label = label
            )
        }
    }
}