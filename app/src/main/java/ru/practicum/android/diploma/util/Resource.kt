package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.data.network.NetworkError

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val networkError: NetworkError? = null
) {
    class Success<T>(
        data: T
    ) : Resource<T>(data)

    class Error<T>(
        networkError: NetworkError? = null,
        message: String = "зачем я тут",
        data: T? = null
    ) : Resource<T>(data, message)
}
