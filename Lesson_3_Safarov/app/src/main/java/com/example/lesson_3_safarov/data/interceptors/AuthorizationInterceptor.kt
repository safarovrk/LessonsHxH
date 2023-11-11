package com.example.lesson_3_safarov.data.interceptors

import com.example.lesson_3_safarov.data.repository.PreferenceStorage
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : Interceptor {

    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer "
        const val SIGN_IN_REQUEST_PATH = "user/signin"
    }

    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {

        val token = preferenceStorage.getValue(PreferenceStorage.PREF_TOKEN_KEY)

        val original = chain.request()

        val request = if (!original.url.encodedPath.contains(SIGN_IN_REQUEST_PATH) || token.isBlank()) {
            original.newBuilder().header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
        } else {
            original.newBuilder()
        }
            .method(original.method, original.body)
            .build()

        chain.proceed(request)
    }
}