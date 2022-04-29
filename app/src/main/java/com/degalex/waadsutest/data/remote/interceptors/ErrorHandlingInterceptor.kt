package com.degalex.waadsutest.data.remote.interceptors

import com.degalex.waadsutest.exceptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            return proceedResponse(chain)
        } catch (exception: IOException) {
            throw exception
        }
    }

    @Throws(NetworkException::class)
    private fun proceedResponse(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful) {

            throw NetworkException(
                code = response.code,
            )
        }

        return response
    }
}
