package com.example.visitus.model.data.server.interceptor

import com.example.visitus.model.data.storage.Prefs
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token : String = Prefs.token
        println("Token = $token")
        if (token != "") {
            try {
                request = request.newBuilder().addHeader("Authorization", "Bearer $token").build()

            } catch (e: IllegalArgumentException) {
                // If token can't be parsed, just logout user to change it with correct value.
            }
        }
        return chain.proceed(request)
    }
}