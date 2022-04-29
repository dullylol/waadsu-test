package com.degalex.waadsutest.exceptions

data class NetworkException(
    val code: Int,
) : Exception()