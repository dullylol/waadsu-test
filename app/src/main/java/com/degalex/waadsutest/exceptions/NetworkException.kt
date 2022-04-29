package com.degalex.waadsutest.exceptions

import java.io.IOException

data class NetworkException(
    val code: Int,
) : IOException()