package ru.practicum.android.diploma.data.network

sealed class NetworkError(override val message: String) : Throwable(message = message) {

    class ServerError(requestName: String, message: String) :
        NetworkError("Error communicating with the server for requestName: $requestName, message: $message")

    class NoData(requestName: String) :
        NetworkError("Empty response body for requestName: $requestName")

    class BadCode(requestName: String, code: Int) :
        NetworkError("Server response with code: $code for requestName: $requestName")

    class NoInternet : NetworkError("No internet connection")

    companion object {
        const val FAILED_INTERNET_CONNECTION_CODE = -1
        const val SUCCESS_CODE = 200
        const val BAD_REQUEST_CODE = 400
        const val NOT_FOUND_CODE = 404
    }
}
