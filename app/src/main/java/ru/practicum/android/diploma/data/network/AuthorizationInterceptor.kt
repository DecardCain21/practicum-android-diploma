package ru.practicum.android.diploma.data.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.practicum.android.diploma.app.NavigationJobApp

object AuthorizationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", "Bearer ${NavigationJobApp.HEAD_HUNTER_TOKEN}")
            .build()
        return chain.proceed(request)
    }

}
